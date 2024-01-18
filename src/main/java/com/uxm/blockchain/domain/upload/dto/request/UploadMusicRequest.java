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
public class UploadMusicRequest {
  private String title;
  private String artist;
  private Genre genre;
  private List<Long> holder;
  private List<Double> rate;
  private String cid1;
  private String settleAddr;
  private MultipartFile music;

  @Builder
  public UploadMusicRequest(String title, String artist, Genre genre, List<Long> holder,
      List<Double> rate,
      String cid1, String settleAddr,
      MultipartFile music) {
    this.title = title;
    this.artist = artist;
    this.genre = genre;
    this.holder = holder;
    this.rate = rate;
    this.cid1 = cid1;
    this.settleAddr = settleAddr;
    this.music = music;
  }
}
