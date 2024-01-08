package com.uxm.blockchain.domain.user.service;

import com.uxm.blockchain.domain.user.dto.response.UserCheckWalletResponse;
import com.uxm.blockchain.domain.user.dto.response.UserSignInResponse;
import com.uxm.blockchain.domain.user.dto.response.UserSignUpResponse;
import com.uxm.blockchain.domain.user.dto.resquest.UserCheckWalletRequest;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignInRequest;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignUpRequest;

public interface UserService {
  public UserSignUpResponse signUp(UserSignUpRequest dto);

  public UserCheckWalletResponse checkWallet(UserCheckWalletRequest dto);

  public UserSignInResponse signIn(UserSignInRequest dto);
}
