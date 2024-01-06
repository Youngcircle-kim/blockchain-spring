package com.uxm.blockchain.domain.user.controller;

import com.uxm.blockchain.common.message.ResponseMessage;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignUpRequestDto;
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
      final @RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto
  ) throws Exception {
    ResponseMessage responseMessage;
    var result = this.userService.signUp(userSignUpRequestDto);

    if(result.getData() == null){
      responseMessage = ResponseMessage.of(HttpStatus.BAD_REQUEST, result.getMessage());
      return new ResponseEntity<>(responseMessage.getHttpStatus());
    }
    responseMessage =ResponseMessage.of(HttpStatus.CREATED, result.getMessage(), result.getData());
    return new ResponseEntity<>(responseMessage, responseMessage.getHttpStatus());
  }
}
