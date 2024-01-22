package com.uxm.blockchain.domain.nft.controller;

import com.uxm.blockchain.common.message.ResponseMessage;
import com.uxm.blockchain.domain.nft.dto.response.CheckMintedMusicResponseDto;
import com.uxm.blockchain.domain.nft.dto.response.GenerateNFTRequestDto;
import com.uxm.blockchain.domain.nft.dto.resquest.CheckMintedMusicRequestDto;
import com.uxm.blockchain.domain.nft.dto.resquest.UploadNFTMetadataRequestDto;
import com.uxm.blockchain.domain.nft.service.NFTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NFTController {

  private final NFTService nftService;

  @GetMapping("/nft/hasMinted")
  public ResponseEntity<ResponseMessage> hasMinted(CheckMintedMusicRequestDto dto)
      throws Exception {
    try {
      CheckMintedMusicResponseDto result = this.nftService.hasMinted(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "NFT 발행여부 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "NFT 발행여부 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }
  @PostMapping("/nft/meta")
  public ResponseEntity<ResponseMessage> uploadMeta(UploadNFTMetadataRequestDto dto) throws Exception {
    try {
      val result = this.nftService.uploadMetaNFT(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "NFT 메타데이터 업로드 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "NFT 메타데이터 업로드 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }

  @PostMapping("/nft/create")
  public ResponseEntity<ResponseMessage> createNft(GenerateNFTRequestDto dto) throws Exception {
    try {
      val result = this.nftService.generateNFT(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "NFT 생성 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "NFT 생성 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }
  @PostMapping("/nft/sell/{id}")
  public ResponseEntity<ResponseMessage> registrationNFT(
      final @PathVariable @Valid Long id,
      final @RequestBody @Valid String txId
  )throws Exception {
    try {
      val result = this.nftService.registNFT(id, txId);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "NFT 판매 등록 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "NFT 판매 등록 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }
}