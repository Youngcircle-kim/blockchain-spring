package com.uxm.blockchain.domain.purchase.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicPurchaseResponse {
  private long id;
  @Builder
  public MusicPurchaseResponse(long id) {
    this.id = id;
  }
}
