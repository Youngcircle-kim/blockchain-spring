package com.uxm.blockchain.domain.upload.model;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayProperty {
  private long songId;
  private List<RightHolder> rightHolders;

  @Builder
  public PayProperty(long songId, List<RightHolder> rightHolders) {
    this.songId = songId;
    this.rightHolders = rightHolders;
  }
}
