package com.uxm.blockchain.domain.nft.service;

import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.nft.dto.response.CheckMintedMusicResponseDto;
import com.uxm.blockchain.domain.nft.dto.resquest.CheckMintedMusicRequestDto;
import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.nft.repository.NFTRepository;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

  private UserDetails getUserInfo(){
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    return (UserDetails) authentication;
  }

}
