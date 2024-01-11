package com.uxm.blockchain.domain.user.controller;

import com.uxm.blockchain.common.message.ResponseMessage;
import com.uxm.blockchain.domain.user.dto.response.UserInfoResponse;
import com.uxm.blockchain.domain.user.dto.request.UserCheckWalletRequest;
import com.uxm.blockchain.domain.user.dto.request.UserFindOneRequest;
import com.uxm.blockchain.domain.user.dto.request.UserSignInRequest;
import com.uxm.blockchain.domain.user.dto.request.UserSignUpRequest;
import com.uxm.blockchain.domain.user.dto.request.UserUpdateRequest;
import com.uxm.blockchain.domain.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

  private final UserServiceImpl userService;

  @PostMapping("/auth/signup")
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

  @PostMapping("/auth/check")
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

  @PostMapping("/auth/signin")
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
  @GetMapping("/user")
  public ResponseEntity<ResponseMessage> userFindOneInfo(
      final @RequestParam("search") @Valid UserFindOneRequest dto
  ) throws Exception {
    try {
      val result = this.userService.findOneInfo(dto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "유저 검색 성공", result);
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    } catch (Exception e){
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, "유저 검색 실패");
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }

  @GetMapping("/user?search={search}")
  public ResponseEntity<ResponseMessage> userInfo(){
    UserInfoResponse result = this.userService.myInfo();
    ResponseMessage responseMessage;
    if (!result.getCheck()){
      responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, result.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
    responseMessage = ResponseMessage.of(HttpStatus.OK, result.getMessage(), result.getUser());
    return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());

  }
  @PutMapping("/user")
  public ResponseEntity<ResponseMessage> updateUserInfo(
      final @RequestBody @Valid UserUpdateRequest userUpdateRequestDto
  ) throws Exception {
    try {
      val result = this.userService.updateInfo(userUpdateRequestDto);
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.OK, "내 정보 수정 성공", result);
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    } catch (Exception e) {
      ResponseMessage responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST,
          "내 정보 수정 실패 - " + e.getMessage());
      return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
    }
  }


}
