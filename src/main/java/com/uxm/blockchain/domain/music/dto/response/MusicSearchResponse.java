package com.uxm.blockchain.domain.music.dto.response;

import com.uxm.blockchain.domain.music.entity.Music;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicSearchResponse {
  private long id;
  private String title;
  private String image;

  @Builder
  public MusicSearchResponse(long id, String title, String image){
    this.id = id;
    this.title = title;
    this.image = image;
  }

  public static MusicSearchResponse from(Music music){
    return MusicSearchResponse
        .builder()
        .id(music.getId())
        .title(music.getTitle())
        .image(music.getCid1())
        .build();
  }

}
