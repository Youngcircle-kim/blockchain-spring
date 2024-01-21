package com.uxm.blockchain.domain.purchase.dto.response;

import com.uxm.blockchain.domain.purchase.model.MusicInfo;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckingPurchasedMusicResponse {
  List<MusicInfo> list;

  @Builder
  public CheckingPurchasedMusicResponse(List<MusicInfo> list) {
    this.list = list;
  }
}
