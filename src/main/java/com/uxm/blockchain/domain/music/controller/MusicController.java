package com.uxm.blockchain.domain.music.controller;

import com.uxm.blockchain.common.message.ResponseMessage;
import com.uxm.blockchain.domain.music.dto.request.CheckMusicChartRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicSearchRequest;
import com.uxm.blockchain.domain.music.service.MusicServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MusicController {

  private final MusicServiceImpl musicService;
  @GetMapping("/music")
  public ResponseEntity<ResponseMessage> musicSearch(
      final @RequestParam(value = "search") @Valid MusicSearchRequest dto
  ){
    try {
      val result = musicService.musicSearch(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "음악 검색 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    }catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "음악 검색 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }
  @GetMapping("/music/chart")
  public ResponseEntity<ResponseMessage> musicChart(
      final @RequestParam(value = "genre") @Valid CheckMusicChartRequest dto
  ){
    return null;
  }

  @GetMapping("/music/{id}")
  public ResponseEntity<ResponseMessage> musicInfo(
      final @PathVariable @Valid Long id
  ){
    try{
      val result = musicService.musicInfo(id);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "음악 정보 검색 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    }
    catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "음악 정보 검색 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }
}
