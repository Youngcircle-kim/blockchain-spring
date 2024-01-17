package com.uxm.blockchain.domain.upload.model;

import com.uxm.blockchain.common.Enum.Genre;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongInfo {
  private String title;
  private long artistId;
  private String artist;
  private String album;
  private String imageCid;
  private Genre genre;
  private String lyrics;
  private List<Long> composerId;
  private List<Long> songWriterId;

  @Builder
  public SongInfo(String title, long artistId, String artist, String album, String imageCid,
      Genre genre, String lyrics, List<Long> composerId, List<Long> songWriterId) {
    this.title = title;
    this.artistId = artistId;
    this.artist = artist;
    this.album = album;
    this.imageCid = imageCid;
    this.genre = genre;
    this.lyrics = lyrics;
    this.composerId = composerId;
    this.songWriterId = songWriterId;
  }

}
