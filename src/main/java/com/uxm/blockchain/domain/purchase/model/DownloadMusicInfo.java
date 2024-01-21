package com.uxm.blockchain.domain.purchase.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DownloadMusicInfo {
  private String title;
  private String artist;
  private String cid3;
  @Builder
  public DownloadMusicInfo(String title, String artist, String cid3) {
    this.title = title;
    this.artist = artist;
    this.cid3 = cid3;
  }
}
