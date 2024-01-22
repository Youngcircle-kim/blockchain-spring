package com.uxm.blockchain.domain.nft.dto.resquest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckMintedMusicRequestDto {
  private long musicId;

  @Builder
  public CheckMintedMusicRequestDto(long musicId) {
    this.musicId = musicId;
  }
}
