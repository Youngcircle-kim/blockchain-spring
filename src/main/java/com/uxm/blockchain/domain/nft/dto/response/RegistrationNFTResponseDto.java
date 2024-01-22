package com.uxm.blockchain.domain.nft.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationNFTResponseDto {
  private long id;

  @Builder
  public RegistrationNFTResponseDto(long id) {
    this.id = id;
  }
}
