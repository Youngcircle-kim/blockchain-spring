package com.uxm.blockchain.domain.upload.model;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Metadata {
  private long uploaderId;
  private String uploadTime;
  private SongInfo songInfo;

  @Builder
  public Metadata(long uploaderId, String uploadTime, SongInfo songInfo) {
    this.uploaderId = uploaderId;
    this.uploadTime = uploadTime;
    this.songInfo = songInfo;
  }
}
