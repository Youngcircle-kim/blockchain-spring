package com.uxm.blockchain.domain.upload.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadMetadataResponse {
  private String cid;

  @Builder
  public UploadMetadataResponse(String cid) {
    this.cid = cid;
  }
}
