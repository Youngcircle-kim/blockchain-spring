package com.uxm.blockchain.domain.user.dto.resquest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignInRequest {
  private String email;
  private String password;

  @Builder
  public UserSignInRequest(String email, String password){
    this.email = email;
    this.password = password;
  }
}
