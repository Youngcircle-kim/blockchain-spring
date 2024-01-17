package com.uxm.blockchain.domain.upload.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckMusicDuplicatedRequest {
  private MultipartFile music;

  @Builder
  public CheckMusicDuplicatedRequest(MultipartFile music) {
    this.music = music;
  }
}
