package com.uxm.blockchain.domain.user.dto.resquest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCheckWalletRequest {
  private String wallet;
  @Builder
  public UserCheckWalletRequest(String wallet){
    this.wallet = wallet;
  }
}
