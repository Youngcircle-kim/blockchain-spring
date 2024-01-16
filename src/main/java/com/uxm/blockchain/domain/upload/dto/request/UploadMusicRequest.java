package com.uxm.blockchain.domain.upload.dto.request;

import com.uxm.blockchain.common.Enum.Genre;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadMusicRequest {
  private String title;
  private long artistId;
  private String artist;
  private String album;
  private MultipartFile image;
  private String lyrics;
  private Genre genre;
  private List<Long> composerId;
  private List<Long> songWriterId;
}
