package com.uxm.blockchain.domain.purchase.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DownloadingMusicResponse {
  private byte[] file;
  private String fileName;

  @Builder
  public DownloadingMusicResponse(byte[] file, String fileName) {
    this.file = file;
    this.fileName = fileName;
  }
}
