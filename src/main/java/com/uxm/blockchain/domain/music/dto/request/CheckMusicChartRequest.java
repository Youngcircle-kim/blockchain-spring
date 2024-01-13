package com.uxm.blockchain.domain.music.dto.request;

import com.uxm.blockchain.common.Enum.Genre;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckMusicChartRequest {
  private Genre genre;

  @Builder
  public CheckMusicChartRequest(Genre genre){
    this.genre = genre;
  }
}
