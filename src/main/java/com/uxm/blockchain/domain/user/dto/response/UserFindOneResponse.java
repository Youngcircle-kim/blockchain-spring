package com.uxm.blockchain.domain.user.dto.response;

import com.uxm.blockchain.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindOneResponse {
  private long id;
  private String nickname;

  @Builder
  public UserFindOneResponse(long id, String nickname){
    this.id = id;
    this.nickname = nickname;
  }

  public static UserFindOneResponse from(User user){
    return UserFindOneResponse.builder()
        .id(user.getId())
        .nickname(user.getNickname())
        .build();
  }

}
