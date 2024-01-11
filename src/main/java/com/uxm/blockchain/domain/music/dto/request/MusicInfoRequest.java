package com.uxm.blockchain.domain.music.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicInfoRequest {
  private long id;

  @Builder
  public MusicInfoRequest ( long id){
    this.id = id;
  }
}
