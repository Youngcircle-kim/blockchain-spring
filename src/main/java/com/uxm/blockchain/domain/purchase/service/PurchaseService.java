package com.uxm.blockchain.domain.purchase.service;

import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.contracts.SettlementContract;
import com.uxm.blockchain.contracts.SettlementContract.LogBuyerInfoEventResponse;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.purchase.dto.response.MusicCheckingPurchasedMusicResponse;
import com.uxm.blockchain.domain.purchase.dto.response.MusicPurchaseResponse;
import com.uxm.blockchain.domain.purchase.entity.Purchase;
import com.uxm.blockchain.domain.purchase.model.MusicInfo;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import io.ipfs.api.IPFS;
import io.ipfs.multibase.Base58;
import io.ipfs.multihash.Multihash;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONObject;
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
      throw new Exception("음원 결제 실패");
    }
  }
  public MusicCheckingPurchasedMusicResponse checkingPurchasedMusic() throws Exception {
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
        String songInfo = jsonObject.getString("songInfo");
        String album = new JSONObject(songInfo).getString("album");

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
      return MusicCheckingPurchasedMusicResponse
          .builder()
          .list(list)
          .build();
    } catch (Exception e){
      throw new Exception("음원 구매 내역 조회 실패");
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
