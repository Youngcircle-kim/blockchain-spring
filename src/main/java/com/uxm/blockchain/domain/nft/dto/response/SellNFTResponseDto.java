package com.uxm.blockchain.domain.nft.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellNFTResponseDto {
  private long id;

  @Builder
  public SellNFTResponseDto(long id) {
    this.id = id;
  }
}
