package com.uxm.blockchain.domain.nft.dto.resquest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenerateNFTResponseDto {
  private long nftId;
  private long userNftId;

  @Builder
  public GenerateNFTResponseDto(long nftId, long userNftId) {
    this.nftId = nftId;
    this.userNftId = userNftId;
  }
}
