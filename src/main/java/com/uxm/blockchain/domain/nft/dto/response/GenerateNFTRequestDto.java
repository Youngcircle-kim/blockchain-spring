package com.uxm.blockchain.domain.nft.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenerateNFTRequestDto {
  private long musicId;
  private String cid;
  private String contractAddr;
  private String txId;

  @Builder
  public GenerateNFTRequestDto(long musicId, String cid, String contractAddr, String txId) {
    this.musicId = musicId;
    this.cid = cid;
    this.contractAddr = contractAddr;
    this.txId = txId;
  }
}
