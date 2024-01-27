package com.uxm.blockchain.domain.purchase.service;

import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.contracts.SettlementContract;
import com.uxm.blockchain.contracts.SettlementContract.LogBuyerInfoEventResponse;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.purchase.dto.response.CheckingPurchasedMusicResponse;
import com.uxm.blockchain.domain.purchase.dto.response.DownloadingMusicResponse;
import com.uxm.blockchain.domain.purchase.dto.response.MusicPurchaseResponse;
import com.uxm.blockchain.domain.purchase.entity.Purchase;
import com.uxm.blockchain.domain.purchase.model.DownloadMusicInfo;
import com.uxm.blockchain.domain.purchase.model.MusicInfo;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import io.ipfs.api.IPFS;
import io.ipfs.multibase.Base58;
import io.ipfs.multihash.Multihash;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseService {
  @Value("${IPFS_ENC_KEY}")
  private String key;
  @Value("${IPFS_ENC_IV}")
  private String iv;
  private final IPFSConfig ipfsConfig;
  private final PurchaseRepository purchaseRepository;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  private final Web3jConfig web3jConfig;

  public MusicPurchaseResponse purchaseMusic(long musicId, String hash) throws Exception {
    try{
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      if (user.isEmpty()) throw new Exception("존재하지 않는 유저입니다.");
      long userId = user.get().getId();

      Web3j web3j = web3jConfig.web3j();
      SettlementContract contract = web3jConfig.settlementContract();
      Optional<TransactionReceipt> transactionReceipt = web3j.ethGetTransactionReceipt(hash).send()
          .getTransactionReceipt();
      if (transactionReceipt.isEmpty()) throw new Exception("Receipt 없음");
      TransactionReceipt receipt = transactionReceipt.get();
      LogBuyerInfoEventResponse logBuyerInfoEventFromLog = contract.getLogBuyerInfoEventFromLog(
          receipt.getLogs().get(0));
      String buyer = logBuyerInfoEventFromLog.buyer;
      List<byte[]> songCidBytes = logBuyerInfoEventFromLog.songCid;

      StringBuilder songCidBuilder = new StringBuilder();
      for (byte[] bytes : songCidBytes) {
        songCidBuilder.append(new String(bytes, StandardCharsets.UTF_8));
      }
      String songCid = songCidBuilder.toString();
      Optional<User> buyerUser = this.userRepository.findByWallet(buyer);
      Optional<Music> boughtMusic = this.musicRepository.findByCid1(songCid);

      if (buyerUser.isEmpty() || boughtMusic.isEmpty()) throw new Exception("존재하지 않는 유저나 음악입니다.");

      Long user_id = buyerUser.get().getId();
      Long music_id = boughtMusic.get().getId();
      if (user_id != userId || musicId != music_id){
          throw new Exception("권한이 없습니다.");
      }
      Purchase purchase = this.purchaseRepository.save(Purchase.builder()
          .user(buyerUser.get())
          .music(boughtMusic.get())
          .build());
      return MusicPurchaseResponse.builder()
          .id(purchase.getId())
          .build();
    } catch (Exception e){
      throw new Exception("음원 결제 실패 " + e.getMessage());
    }
  }
  public CheckingPurchasedMusicResponse checkingPurchasedMusic() throws Exception {
    try{
      List<MusicInfo> list = new ArrayList<>();
      IPFS ipfs = ipfsConfig.getIpfs();
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      if (user.isEmpty()) throw new Exception("권한이 없습니다.");

      List<Purchase> purchases = this.purchaseRepository.findAllByUser(user.get());

      for (int i = 0; i < purchases.size(); i++){
        Optional<Music> music = this.musicRepository.findById(purchases.get(i).getMusic().getId());
        if (music.isEmpty()) throw new Exception("권한이 없습니다.");
        byte[] chunks = ipfs.cat(Multihash.fromBase58(music.get().getCid1()));
        String meta = new String(chunks, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(meta);
        JSONObject songInfo = jsonObject.getJSONObject("songInfo");
        String album =songInfo.getString("album");

        String imageCid = new JSONObject(songInfo).getString("imageCid");
        byte[] image = ipfs.cat(Multihash.fromBase58(imageCid));
        byte[] decoded = Base58.decode(new String(image, StandardCharsets.UTF_8));

        list.add(MusicInfo.builder()
            .id(music.get().getId())
            .title(music.get().getTitle())
            .artist(music.get().getArtist())
            .album(album)
            .image(decoded.toString())
            .build());
      }
      return CheckingPurchasedMusicResponse
          .builder()
          .list(list)
          .build();
    } catch (Exception e){
      throw new Exception("음원 구매 내역 조회 실패");
    }
  }
  public DownloadingMusicResponse downloadMusic(long musicId, String token) throws Exception {
    try{
      IPFS ipfs = ipfsConfig.getIpfs();
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      Optional<Music> music = this.musicRepository.findById(musicId);
      if (user.isEmpty() || music.isEmpty()) throw new Exception("권한이 없습니다.");

      Optional<Purchase> buy = this.purchaseRepository.findByUserAndMusic(user.get(),
          music.get());
      if (buy.isEmpty()) throw new Exception("권한이 없습니다.");

      Optional<Music> music1 = this.musicRepository.findById(musicId);
      if (music1.isEmpty()) throw new Exception("권한이 없습니다.");

      DownloadMusicInfo info = DownloadMusicInfo.builder()
          .title(music1.get().getTitle())
          .artist(music1.get().getArtist())
          .cid3(music1.get().getCid3())
          .build();

      String filename = info.getArtist() + "-" + info.getTitle() + ".mp3";

      byte[] chunks = ipfs.cat(Multihash.fromBase58(info.getCid3()));
      SecretKeySpec aes = new SecretKeySpec(this.key.getBytes(StandardCharsets.UTF_8), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(
          this.iv.getBytes(StandardCharsets.UTF_8));

      Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
      cipher.init(Cipher.DECRYPT_MODE, aes, ivParameterSpec);

      byte[] decrypted = cipher.doFinal(chunks);

      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decrypted);
      GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

      byte[] buffer = new byte[1024];
      int len;
      while ((len = gzipInputStream.read(buffer)) > 0) {
        byteArrayOutputStream.write(buffer, 0, len);
      }

      return DownloadingMusicResponse.builder()
          .file(byteArrayOutputStream.toByteArray())
          .fileName(filename)
          .build();
    } catch (Exception e){
      throw new Exception("음악 다운로드 실패");
    }
  }
  private UserDetails getUserInfo(){
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    return (UserDetails) authentication;
  }

}
