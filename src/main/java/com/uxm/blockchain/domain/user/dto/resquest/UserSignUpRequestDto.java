package com.uxm.blockchain.domain.user.dto.resquest;

import com.uxm.blockchain.common.Enum.Type;
import com.uxm.blockchain.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSignUpRequestDto {
  private String email;
  private String password;
  private String name;
  private Type type;
  private String nickname;
  private String wallet;

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
