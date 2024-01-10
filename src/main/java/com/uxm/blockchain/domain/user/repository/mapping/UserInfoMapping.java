package com.uxm.blockchain.domain.user.repository.mapping;

import com.uxm.blockchain.common.Enum.Type;

public interface UserInfoMapping {
  Long getId();
  String getEmail();
  String getName();
  Type getType();
  String getNickname();
  String getWallet();
}
