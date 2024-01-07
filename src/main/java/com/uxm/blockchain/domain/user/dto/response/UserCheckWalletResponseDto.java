package com.uxm.blockchain.domain.user.dto.response;

import com.uxm.blockchain.domain.user.dto.resquest.UserCheckWalletRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserCheckWalletResponseDto {
  private String message;
  private boolean accept;

  public static UserCheckWalletResponseDto from(String message, boolean accept){
    return UserCheckWalletResponseDto
        .builder()
        .message(message)
        .accept(accept)
        .build();
  }
}
