package com.uxm.blockchain.domain.nft.service;

import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.contracts.NFT1155;
import com.uxm.blockchain.contracts.NFT1155.TransferSingleEventResponse;
import com.uxm.blockchain.contracts.SettlementContractExtra;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.nft.dto.response.CheckMintedMusicResponseDto;
import com.uxm.blockchain.domain.nft.dto.response.GenerateNFTRequestDto;
import com.uxm.blockchain.domain.nft.dto.response.RegistrationNFTResponseDto;
import com.uxm.blockchain.domain.nft.dto.response.SellNFTResponseDto;
import com.uxm.blockchain.domain.nft.dto.response.UploadNFTMetadataResponseDto;
import com.uxm.blockchain.domain.nft.dto.resquest.CheckMintedMusicRequestDto;
import com.uxm.blockchain.domain.nft.dto.resquest.GenerateNFTResponseDto;
import com.uxm.blockchain.domain.nft.dto.resquest.UploadNFTMetadataRequestDto;
import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.nft.repository.NFTRepository;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import com.uxm.blockchain.domain.user_nft.entity.User_nft;
import com.uxm.blockchain.domain.user_nft.repository.UserNftRepository;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multihash.Multihash;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONArray;
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
public class NFTService {
  private final IPFSConfig ipfsConfig;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  private final NFTRepository nftRepository;
  private final Web3jConfig web3jConfig;
  private final UserNftRepository userNftRepository;

  public CheckMintedMusicResponseDto hasMinted(CheckMintedMusicRequestDto dto) throws Exception {
    try{
      String email = getUserInfo().getUsername();
      User user = this.userRepository.findByEmail(email).get();
      long musicId = dto.getMusicId();
      Optional<Music> music = this.musicRepository.findById(musicId);
      if (music.isEmpty()) throw new Exception("음악이 존재하지 않습니다.");

      Optional<Nft> nft = this.nftRepository.findByUserAndMusic(user, music.get());

      return CheckMintedMusicResponseDto.builder()
          .hasMinted(nft.isPresent())
          .build();
    }catch (Exception e){
      throw new Exception("NFT 발행 여부 조회 실패");
    }
  }
  public UploadNFTMetadataResponseDto uploadMetaNFT(UploadNFTMetadataRequestDto dto)
      throws Exception {
    try{
      IPFS ipfs = ipfsConfig.getIpfs();
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      if (user.isEmpty()) throw new Exception("유저 조회 실페");
      Long userId = user.get().getId();
      String userIdString = userId.toString();

      Optional<Music> music = this.musicRepository.findById(dto.getMusicId());
      if (music.isEmpty()) throw new Exception("음원 조회 실패");

      byte[] data = ipfs.cat(Multihash.fromBase58(music.get().getCid2()));
      JSONObject copyrightInfo = new JSONObject(new String(data, StandardCharsets.UTF_8));
      JSONArray holders = copyrightInfo.getJSONObject("payProperty").getJSONArray("rightHolders");

      JSONObject uploader = null;
      for (int i = holders.length() - 1; i >= 0; i--) {
        JSONObject holder = holders.getJSONObject(i);
        if (userIdString.equals(holder.getString("userId"))) {
          uploader = holder;
          break;
        }
      }
      if (uploader.isEmpty()) throw new Exception("권한이 없습니다.");

      int holderNo = -1;
      for (int i = 0; i < holders.length(); i++) {
        JSONObject holder = holders.getJSONObject(i);
        if (userIdString.equals(holder.getString("userId"))) {
          holderNo = i + 1;
          break;
        }
      }
      JSONObject mainContent = new JSONObject();
      mainContent.put("cid1", music.get().getCid1());
      mainContent.put("cid2", music.get().getCid2());

      JSONObject meta = new JSONObject();
      meta.put("name", holderNo + "/" + holders.length());
      meta.put("minter", uploader.getString("walletAddress"));
      meta.put("mainContent", mainContent);
      meta.put("proportion", uploader.getInt("proportion"));
      meta.put("supply", 1);
      ByteArrayWrapper metaFile = new ByteArrayWrapper(
          meta.toString().getBytes(StandardCharsets.UTF_8));

      MerkleNode merkleNode = ipfs.add(metaFile).get(0);
      String cid = merkleNode.hash.toBase58();

      return UploadNFTMetadataResponseDto.builder()
          .cid(cid)
          .build();
    } catch (Exception e){
      throw new Exception("NFT 메타데이터 업로드 실패");
    }
  }
  public GenerateNFTResponseDto generateNFT(GenerateNFTRequestDto dto) throws Exception{
    try{
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      Optional<Music> music = this.musicRepository.findById(dto.getMusicId());
      if (user.isEmpty() || music.isEmpty()) throw new Exception("유저나 음악 정보가 올바르지 않습니다.");

      boolean isValid = validate(dto.getCid(), dto.getContractAddr(), dto.getCid());
      if (!isValid) throw new Exception("컨트랙트 정보가 올바르지 않습니다.");

      Nft nft = this.nftRepository.save(Nft.builder()
          .user(user.get())
          .music(music.get())
          .contractAddress(dto.getContractAddr())
          .cid(dto.getCid())
          .build());
      User_nft userNft = this.userNftRepository.save(User_nft.builder()
          .user(user.get())
          .nft(nft)
          .sell_tx(dto.getTxId())
          .is_sale(true)
          .build());
      return GenerateNFTResponseDto.builder()
          .nftId(nft.getId())
          .userNftId(userNft.getId())
          .build();
    } catch (Exception e){
      throw new Exception("NFT 생성 실패");
    }
  }
  public RegistrationNFTResponseDto registNFT(long id, String txId) throws Exception {
    try {
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      if (user.isEmpty()) throw new Exception("유저 정보가 올바르지 않습니다.");

      boolean validate = validate(txId);
      if (!validate) throw new Exception("컨트랙트 정보가 올바르지 않습니다.");
      Optional<User_nft> userNft = this.userNftRepository.findByUserAndId(user.get(), id);
      if (userNft.isEmpty()) throw new Exception("권한이 없습니다.");

      User_nft user_nft = updateUserNft1(userNft.get(), txId);

      return RegistrationNFTResponseDto.builder()
          .id(user_nft.getId())
          .build();
    } catch (Exception e){
      throw new Exception("NFT 판매 등록 실패");
    }
  }

  public SellNFTResponseDto sellNFT(long id, String txId) throws Exception {
    try {
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);

      boolean isValid = validateOwner(txId);
      if (!isValid) throw new Exception("컨트랙트 정보가 올바르지 않습니다.");

      Optional<User_nft> userNft = userNftRepository.findById(id);

      User_nft user_nft = updateUserNft2(userNft.get(), user.get().getId().toString(), txId);

      return SellNFTResponseDto.builder()
          .id(user_nft.getId())
          .build();
    }catch (Exception e) {
      throw new Exception("");
    }
  }

  public List<Nft> checkPurchasedNft(String musicId) throws Exception {
    try{
      if (musicId.isBlank()) return this.nftRepository.findOnSale();
      else return this.nftRepository.findByMusicId(Long.valueOf(musicId));
    }catch (Exception e){
      throw new Exception("nft 판매 목록 조회 실패");
    }
  }
  public List<Nft> myPurchaseNft() throws Exception {
    try {
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      return this.nftRepository.findByUserId(user.get().getId());
    } catch (Exception e){
      throw new Exception("나의 nft 판매 목록 조회 실패");
    }
  }
  public Nft checkNftById(long id) throws Exception {
    try{
      Optional<Nft> nft = this.nftRepository.findById(id);
      return nft.get();
    }catch (Exception e){
      throw new Exception("NFT 조회 실패");
    }
  }
  @Transactional
  public User_nft updateUserNft2(User_nft userNft, String userId,String txId){
    userNft.setSell_tx(userId);
    userNft.setPurchase_tx(txId);
    userNft.setIs_sale(false);
    return userNft;
  }
  @Transactional
  public User_nft updateUserNft1(User_nft userNft, String txId){
    userNft.setSell_tx(txId);
    userNft.setPurchase_tx(null);
    userNft.setIs_sale(true);
    return userNft;
  }
  private boolean validateOwner(String txId) throws Exception{
    Web3j web3 = web3jConfig.web3j();
    NFT1155 nft = web3jConfig.nft();
    Optional<TransactionReceipt> transactionReceipt = web3.ethGetTransactionReceipt(txId).send()
        .getTransactionReceipt();

    if (transactionReceipt.isEmpty()) throw new Exception("트랜잭션 Receipt 실패");
    TransactionReceipt receipt = transactionReceipt.get();

    String currentOwner = nft.owner().send();
    return currentOwner.equalsIgnoreCase(receipt.getFrom());
  }
  private boolean validate(String txId) throws Exception {
    Web3j web3 = web3jConfig.web3j();
    NFT1155 nft = web3jConfig.nft();
    SettlementContractExtra settlementContractExtra = web3jConfig.settlementContractExtra();
    Optional<TransactionReceipt> transactionReceipt = web3.ethGetTransactionReceipt(txId).send()
        .getTransactionReceipt();

    if (transactionReceipt.isEmpty()) throw new Exception("트랜잭션 Receipt 실패");
    TransactionReceipt receipt = transactionReceipt.get();
    return nft.isApprovedForAll(receipt.getFrom(), receipt.getTo()).send();
  }
  private boolean validate(String cid, String contractAddr, String txId) throws Exception {
    Web3j web3 = web3jConfig.web3j();
    NFT1155 nft = web3jConfig.nft();
    SettlementContractExtra settlementContractExtra = web3jConfig.settlementContractExtra();
    Optional<TransactionReceipt> transactionReceipt = web3.ethGetTransactionReceipt(txId).send()
        .getTransactionReceipt();

    if (transactionReceipt.isEmpty()) throw new Exception("트랜잭션 Receipt 실패");
    TransactionReceipt receipt = transactionReceipt.get();
    TransferSingleEventResponse transferSingleEventFromLog = nft.getTransferSingleEventFromLog(
        receipt.getLogs().get(0));

    if(!transferSingleEventFromLog.to.equalsIgnoreCase(contractAddr)) return false;

    String addr = settlementContractExtra.nftContractAddresses(nft.owner().send()).send();
    if (addr.equalsIgnoreCase(contractAddr)) return false;

    String songCid = nft.dir().send();
    if (!cid.equals(songCid)) return false;

    return true;
  }

  private UserDetails getUserInfo(){
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    return (UserDetails) authentication;
  }

}
