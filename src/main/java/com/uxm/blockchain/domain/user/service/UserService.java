package com.uxm.blockchain.domain.user.service;

import com.uxm.blockchain.domain.user.dto.response.UserCheckWalletResponse;
import com.uxm.blockchain.domain.user.dto.response.UserFindOneResponse;
import com.uxm.blockchain.domain.user.dto.response.UserInfoResponse;
import com.uxm.blockchain.domain.user.dto.response.UserSignInResponse;
import com.uxm.blockchain.domain.user.dto.response.UserSignUpResponse;
import com.uxm.blockchain.domain.user.dto.response.UserUpdateResponse;
import com.uxm.blockchain.domain.user.dto.request.UserCheckWalletRequest;
import com.uxm.blockchain.domain.user.dto.request.UserFindOneRequest;
import com.uxm.blockchain.domain.user.dto.request.UserSignInRequest;
import com.uxm.blockchain.domain.user.dto.request.UserSignUpRequest;
import com.uxm.blockchain.domain.user.dto.request.UserUpdateRequest;

public interface UserService {
  public UserSignUpResponse signUp(UserSignUpRequest dto);

  public UserCheckWalletResponse checkWallet(UserCheckWalletRequest dto);

  public UserSignInResponse signIn(UserSignInRequest dto);

  public UserInfoResponse myInfo();

  public UserUpdateResponse updateInfo(UserUpdateRequest dto) throws Exception;

  public UserFindOneResponse findOneInfo(UserFindOneRequest dto) throws Exception;
}
