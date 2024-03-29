package com.uxm.blockchain.domain.purchase.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicInfo {
  private long id;
  private String title;
  private String artist;
  private String album;
  private String image;

  @Builder
  public MusicInfo(long id, String title, String artist, String album, String image) {
    this.id = id;
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.image = image;
  }
}
