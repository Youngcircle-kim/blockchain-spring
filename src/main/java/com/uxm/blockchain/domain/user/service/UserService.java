package com.uxm.blockchain.domain.user.service;

import com.uxm.blockchain.domain.user.dto.response.UserCheckWalletResponseDto;
import com.uxm.blockchain.domain.user.dto.response.UserSignUpResponseDto;
import com.uxm.blockchain.domain.user.dto.resquest.UserCheckWalletRequestDto;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignUpRequestDto;

public interface UserService {
  public UserSignUpResponseDto signUp(UserSignUpRequestDto dto) throws Exception;
  public UserCheckWalletResponseDto checkWallet(UserCheckWalletRequestDto dto) throws Exception;
}
