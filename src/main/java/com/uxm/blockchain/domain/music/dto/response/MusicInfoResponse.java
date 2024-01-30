package com.uxm.blockchain.domain.music.dto.response;

import com.uxm.blockchain.common.Enum.Genre;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicInfoResponse {
  private long id;
  private String title;
  private long artistId;
  private String artist;
  private String album;
  private String image;
  private String lyrics;
  private Genre genre;
  private JSONArray composerId;
  private List<String> composer;
  private JSONArray songWriterId;
  private List<String> songWriter;
  @Builder
  public MusicInfoResponse(long id, String title, long artistId, String artist, String album,
      String image, String lyrics, Genre genre, JSONArray composerId, List<String> composer,
      JSONArray songWriterId, List<String> songWriter) {
    this.id = id;
    this.title = title;
    this.artistId = artistId;
    this.artist = artist;
    this.album = album;
    this.image = image;
    this.lyrics = lyrics;
    this.genre = genre;
    this.composerId = composerId;
    this.composer = composer;
    this.songWriterId = songWriterId;
    this.songWriter = songWriter;
  }
}
