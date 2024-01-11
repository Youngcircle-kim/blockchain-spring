package com.uxm.blockchain.domain.music.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicSearchRequest {
  private String search;

  @Builder
  public MusicSearchRequest(String search){
    this.search = search;
  }
}
