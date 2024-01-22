package com.uxm.blockchain.domain.upload.controller;

import com.uxm.blockchain.common.message.ResponseMessage;
import com.uxm.blockchain.domain.upload.dto.request.CheckMusicDuplicatedRequest;
import com.uxm.blockchain.domain.upload.dto.request.UploadMetadataRequest;
import com.uxm.blockchain.domain.upload.dto.request.UploadMusicRequest;
import com.uxm.blockchain.domain.upload.service.UploadServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UploadController {
  private final UploadServiceImpl uploadService;

  @GetMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadMusicInfo(){
    try{
      val result =this.uploadService.uploadMusicInfo();
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "업로드한 음원 조회 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "업로드한 음원 조회 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }

  @PostMapping("/upload/meta")
  public ResponseEntity<ResponseMessage> metadataUpload(UploadMetadataRequest dto){
    try{
      val result = this.uploadService.uploadMetadata(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "메타 데이터 업로드 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "매타 데이터 업로드 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }

  @PostMapping("/upload/check")
  public ResponseEntity<ResponseMessage> checkMusicDuplicated(CheckMusicDuplicatedRequest dto){
    try{
      val result = this.uploadService.checkMusicDuplicated(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "중복 곡 체크 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "중복 곡 체크 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }

  @DeleteMapping("/upload/{id}")
  public ResponseEntity<ResponseMessage> deleteMusic(
      final @PathVariable @Valid Long id
  ) {
    try{
      val result = this.uploadService.deleteMusic(id);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "음원 삭제 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "음원 삭제 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadMusic(
      final @RequestBody @Valid UploadMusicRequest dto
  ){
    try{
      val result = this.uploadService.uploadMusic(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "음원 업로드 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "음원 업로드 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }
}
