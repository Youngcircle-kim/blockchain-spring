package com.uxm.blockchain.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class UserSignUpResponseDto {
  private String message;
  private Object data;

  @Builder
  public UserSignUpResponseDto(Object data, String message){
    this.data = data;
    this.message = message;
  }
  public static UserSignUpResponseDto from(String message){
    return UserSignUpResponseDto
        .builder()
        .data(null)
        .message(message)
        .build();
  }
  public static UserSignUpResponseDto from(Object data, String message){
    return UserSignUpResponseDto
        .builder()
        .data(data)
        .message(message)
        .build();
  }
}
