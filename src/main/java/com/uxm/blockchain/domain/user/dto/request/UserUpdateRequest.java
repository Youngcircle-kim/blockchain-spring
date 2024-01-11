package com.uxm.blockchain.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {
  private String name;
  private String nickname;
  private String password;

  @Builder
  public UserUpdateRequest(String name, String nickname, String password){
    this.name = name;
    this.nickname = nickname;
    this.password = password;
  }

}
