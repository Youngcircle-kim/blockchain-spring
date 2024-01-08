package com.uxm.blockchain.domain.user.controller;

import com.uxm.blockchain.common.message.ResponseMessage;
import com.uxm.blockchain.domain.user.dto.resquest.UserCheckWalletRequest;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignInRequest;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignUpRequest;
import com.uxm.blockchain.domain.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

  private final UserServiceImpl userService;

  @PostMapping("/signup")
  public ResponseEntity<ResponseMessage> signUp(
      final @RequestBody @Valid UserSignUpRequest userSignUpRequestDto
  ) throws Exception {
    var result = this.userService.signUp(userSignUpRequestDto);
    ResponseMessage responseMessage;

    if(result.getData() == null){
      responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, result.getMessage());
      return new ResponseEntity<>(responseMessage.getHttpStatus());
    }
    responseMessage =ResponseMessage.of(HttpStatus.CREATED, result.getMessage(), result.getData());
    return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
  }

  @PostMapping("/check")
  public ResponseEntity<ResponseMessage> checkWallet(
      final @RequestBody @Valid UserCheckWalletRequest userCheckWalletRequestDto
  ) throws Exception {
    var result = userService.checkWallet(userCheckWalletRequestDto);
    ResponseMessage responseMessage;

    if (!result.isAccept()) {
      responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, result.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
    responseMessage = ResponseMessage.of(HttpStatus.CREATED, result.getMessage());
    return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
  }

  @PostMapping("/signin")
  public ResponseEntity<ResponseMessage> signIn(
     final @RequestBody @Valid UserSignInRequest userSignInRequestDto
  )throws Exception{
    var result = userService.signIn(userSignInRequestDto);
    ResponseMessage responseMessage;
    if(result.getJwtToken() == null){
      responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, result.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
    responseMessage = ResponseMessage.of(HttpStatus.OK, result.getMessage(), result.getJwtToken());
    return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
  }
}
