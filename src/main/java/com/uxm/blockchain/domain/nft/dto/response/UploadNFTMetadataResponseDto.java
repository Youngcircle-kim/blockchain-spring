package com.uxm.blockchain.domain.nft.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadNFTMetadataResponseDto {
  private String cid;
  @Builder
  public UploadNFTMetadataResponseDto(String cid) {
    this.cid = cid;
  }
}
