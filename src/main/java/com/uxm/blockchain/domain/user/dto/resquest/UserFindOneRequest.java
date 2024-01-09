package com.uxm.blockchain.domain.user.dto.resquest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindOneRequest {
  private String search;

  @Builder
  public UserFindOneRequest(String search){
    this.search = search;
  }
}
