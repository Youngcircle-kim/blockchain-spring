package com.uxm.blockchain.domain.purchase.controller;

import com.uxm.blockchain.common.message.ResponseMessage;
import com.uxm.blockchain.domain.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PurchaseController {
  private final PurchaseService purchaseService;

  @PostMapping("/purchase/{id}")
  public ResponseEntity<ResponseMessage> purchaseMusic(
      final @PathVariable @Valid Long id,
      final @Valid String hash
  ) {
    try {
      val result = this.purchaseService.purchaseMusic(id, hash);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "음악 구매 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e) {
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST,
          "음악 구매 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }

  @GetMapping("/purchase")
  public ResponseEntity<ResponseMessage> checkPurchasedMusic(){
    try {
      val result = this.purchaseService.checkingPurchasedMusic();
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "음악 구매 내역 조회 성공", result);
      return new ResponseEntity<>(responseMessage,responseMessage.getHttpStatus());
    } catch (Exception e) {
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST,
          "음악 구매 내역 조회 실패 " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }
}
