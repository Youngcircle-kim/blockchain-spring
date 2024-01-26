package com.uxm.blockchain.domain.upload.dto.response;

import com.uxm.blockchain.domain.music.entity.Music;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadMusicInfoResponse {
  private long id;
  private String title;
  private String album;
  private String artist;
  private String image;

  @Builder
  public UploadMusicInfoResponse(long id, String title, String album, String artist, String image){
    this.id = id;
    this.title = title;
    this.album = album;
    this.artist = artist;
    this.image = image;
  }

  public static UploadMusicInfoResponse from(Music music){
    return UploadMusicInfoResponse.builder()
        .id(music.getId())
        .title(music.getTitle())
        .album(null)
        .artist(music.getArtist())
        .image(null)
        .build();
  }
}
