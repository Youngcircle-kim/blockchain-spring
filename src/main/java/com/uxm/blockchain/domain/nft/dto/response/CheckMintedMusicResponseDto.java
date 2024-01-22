package com.uxm.blockchain.domain.nft.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckMintedMusicResponseDto {
  private boolean hasMinted;

  @Builder
  public CheckMintedMusicResponseDto(boolean hasMinted) {
    this.hasMinted = hasMinted;
  }
}
