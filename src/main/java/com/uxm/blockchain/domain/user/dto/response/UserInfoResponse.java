package com.uxm.blockchain.domain.user.dto.response;

import com.uxm.blockchain.domain.user.repository.mapping.UserInfoMapping;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {
  private UserInfoMapping user;
  private String message;
  private Boolean check;


  @Builder
  public UserInfoResponse(UserInfoMapping user, String message,Boolean check) {
    this.user = user;
    this.message = message;
    this.check = check;
  }
  public static UserInfoResponse from(String message){
    return UserInfoResponse.builder()
        .message(message)
        .check(false)
        .build();
  }
  public static UserInfoResponse from(UserInfoMapping user, String message){
    return UserInfoResponse.builder()
        .user(user)
        .message(message)
        .check(true)
        .build();
  }
}
