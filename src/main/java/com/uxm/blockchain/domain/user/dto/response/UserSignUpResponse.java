package com.uxm.blockchain.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignUpResponse {
  private String message;
  private Object data;

  @Builder
  public UserSignUpResponse(Object data, String message){
    this.data = data;
    this.message = message;
  }
  public static UserSignUpResponse from(String message){
    return UserSignUpResponse
        .builder()
        .data(null)
        .message(message)
        .build();
  }
  public static UserSignUpResponse from(Object data, String message){
    return UserSignUpResponse
        .builder()
        .data(data)
        .message(message)
        .build();
  }
}
