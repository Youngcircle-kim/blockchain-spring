package com.uxm.blockchain.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCheckWalletResponse {
  private String message;
  private boolean accept;

  @Builder
  public UserCheckWalletResponse(String message, boolean accept){
    this.message = message;
    this.accept = accept;
  }

  public static UserCheckWalletResponse from(String message, boolean accept){
    return UserCheckWalletResponse
        .builder()
        .message(message)
        .accept(accept)
        .build();
  }
}
