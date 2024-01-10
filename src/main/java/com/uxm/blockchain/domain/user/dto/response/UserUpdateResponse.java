package com.uxm.blockchain.domain.user.dto.response;

import com.uxm.blockchain.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateResponse {
  private long id;
  private String name;
  private String nickname;

  @Builder
  public UserUpdateResponse(long id, String name, String nickname){
    this.id = id;
    this.name = name;
    this.nickname = nickname;
  }

  public static UserUpdateResponse from(User user){
    return UserUpdateResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .nickname(user.getNickname())
        .build();
  }

}
