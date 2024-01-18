package com.uxm.blockchain.domain.upload.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadMusicResponse {
  private long id;
  @Builder
  public UploadMusicResponse(long id) {
    this.id = id;
  }
}
