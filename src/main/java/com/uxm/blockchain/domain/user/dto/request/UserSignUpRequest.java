package com.uxm.blockchain.domain.user.dto.request;

import com.uxm.blockchain.common.Enum.Type;
import com.uxm.blockchain.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpRequest {
  private String email;
  private String password;
  private String name;
  private Type type;
  private String nickname;
  private String wallet;

  @Builder
  public UserSignUpRequest(String email, String password, String name, Type type, String nickname, String wallet){
    this.email = email;
    this.password = password;
    this.name = name;
    this.type = type;
    this.nickname = nickname;
    this.wallet = wallet;
  }

  public User toEntity(){
    return User.builder()
        .email(this.email)
        .password(this.password)
        .name(this.name)
        .type(this.type)
        .nickname(this.nickname)
        .wallet(this.wallet)
        .build();
  }
}
