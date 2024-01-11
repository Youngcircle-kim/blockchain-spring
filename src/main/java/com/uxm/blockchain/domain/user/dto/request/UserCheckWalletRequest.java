package com.uxm.blockchain.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCheckWalletRequest {
  private String wallet;
  @Builder
  public UserCheckWalletRequest(String wallet){
    this.wallet = wallet;
  }
}
