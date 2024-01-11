package com.uxm.blockchain.domain.music.dto.response;

import com.uxm.blockchain.common.Enum.Genre;
import com.uxm.blockchain.domain.music.entity.Music;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicInfoResponse {
  private long id;
  private String title;
  private long user_id;
  private String cid1;
  private String cid2;
  private String cid3;
  private String sha1;
  private String address;
  private Genre genre;

  @Builder
  public MusicInfoResponse(
      long id,
      String title,
      String cid1,
      String cid2,
      String cid3,
      String sha1,
      String address,
      Genre genre,
      long user_id
  ){
    this.id = id;
    this.title = title;
    this.cid1 = cid1;
    this.cid2 = cid2;
    this.cid3 = cid3;
    this.sha1 = sha1;
    this.address = address;
    this.genre = genre;
    this.user_id = user_id;
  }

  public static MusicInfoResponse from(Music music){
    return MusicInfoResponse.builder()
        .id(music.getId())
        .title(music.getTitle())
        .cid1(music.getCid1())
        .cid2(music.getCid2())
        .cid3(music.getCid3())
        .sha1(music.getSha1())
        .address(music.getAddress())
        .genre(music.getGenre())
        .user_id(music.getUser().getId())
        .build();
  }
}
