package com.uxm.blockchain.domain.upload.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckMusicDuplicatedResponse {
  private boolean isDuplicated;

  @Builder
  public CheckMusicDuplicatedResponse(boolean isDuplicated) {
    this.isDuplicated = isDuplicated;
  }
}
