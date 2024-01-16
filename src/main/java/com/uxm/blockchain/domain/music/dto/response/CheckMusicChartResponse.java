package com.uxm.blockchain.domain.music.dto.response;

import com.uxm.blockchain.domain.music.entity.Music;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckMusicChartResponse {
  private long id;
  private String title;
  private String image;
  private String artist;

  @Builder
  public CheckMusicChartResponse(long id, String title, String image, String artist){
    this.id = id;
    this.title = title;
    this.image = image;
    this.artist = artist;
  }

  public static CheckMusicChartResponse from(Music music){
    return CheckMusicChartResponse
        .builder()
        .id(music.getId())
        .title(music.getTitle())
        .image(music.getCid1())
        .artist(music.getArtist())
        .build();
  }
}
