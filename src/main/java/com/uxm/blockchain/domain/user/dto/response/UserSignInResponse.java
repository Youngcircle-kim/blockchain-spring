package com.uxm.blockchain.domain.user.dto.response;

import com.uxm.blockchain.jwt.JwtToken;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignInResponse {
  private String message;
  private JwtToken jwtToken;
  @Builder
  public UserSignInResponse(String message, JwtToken jwtToken){
    this.message = message;
    this.jwtToken = jwtToken;
  }
}
