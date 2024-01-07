package com.uxm.blockchain.domain.user.dto.resquest;

import com.uxm.blockchain.domain.user.dto.response.UserCheckWalletResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserCheckWalletRequestDto {
  private String wallet;
  public UserCheckWalletRequestDto(){
  }
}
