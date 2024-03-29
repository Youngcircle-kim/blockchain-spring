package com.uxm.blockchain.domain.upload.dto.request;

import com.uxm.blockchain.common.Enum.Genre;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadMetadataRequest {
  private String title;
  private long artistId;
  private String artist;
  private String album;
  private MultipartFile image;
  private String lyrics;
  private Genre genre;
  private List<Long> composerId;
  private List<Long> songWriterId;

  @Builder
  public UploadMetadataRequest(String
      title, long artistId, String artist, String album,
      MultipartFile image, String lyrics, Genre genre, List<Long> composerId,
      List<Long> songWriterId) {
    this.title = title;
    this.artistId = artistId;
    this.artist = artist;
    this.album = album;
    this.image = image;
    this.lyrics = lyrics;
    this.genre = genre;
    this.composerId = composerId;
    this.songWriterId = songWriterId;
  }
}
