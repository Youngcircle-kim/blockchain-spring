package com.uxm.blockchain.domain.nft.service;

import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.nft.dto.response.CheckMintedMusicResponseDto;
import com.uxm.blockchain.domain.nft.dto.response.UploadNFTMetadataResponseDto;
import com.uxm.blockchain.domain.nft.dto.resquest.CheckMintedMusicRequestDto;
import com.uxm.blockchain.domain.nft.dto.resquest.UploadNFTMetadataRequestDto;
import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.nft.repository.NFTRepository;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NFTService {
  @Value("${IPFS_ENC_KEY}")
  private String key;
  @Value("${IPFS_ENC_IV}")
  private String iv;
  private final IPFSConfig ipfsConfig;
  private final PurchaseRepository purchaseRepository;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  private final NFTRepository nftRepository;
  private final Web3jConfig web3jConfig;

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

  private UserDetails getUserInfo(){
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    return (UserDetails) authentication;
  }

}
