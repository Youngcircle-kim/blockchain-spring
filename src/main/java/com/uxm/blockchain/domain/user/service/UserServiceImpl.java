package com.uxm.blockchain.domain.user.service;

import com.uxm.blockchain.domain.user.dto.response.UserCheckWalletResponseDto;
import com.uxm.blockchain.domain.user.dto.response.UserSignUpResponseDto;
import com.uxm.blockchain.domain.user.dto.resquest.UserCheckWalletRequestDto;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignUpRequestDto;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional()
@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserSignUpResponseDto signUp(UserSignUpRequestDto dto) throws Exception {
    if (userRepository.findByEmail(dto.getEmail()).isPresent()){
      return UserSignUpResponseDto.from("회원가입 실패 - 이미 존재하는 이메일입니다.");
    }
    if(userRepository.findByNickname(dto.getNickname()).isPresent()){
      return UserSignUpResponseDto.from("회원가입 실패 - 이미 존재하는 닉네임입니다.");
    }
    User user = userRepository.save(dto.toEntity());
    user.encodePassword(passwordEncoder);
    return UserSignUpResponseDto.from(userRepository.findAllByEmail(user.getEmail()), "회원가입 성공");
  }

  @Override
  public UserCheckWalletResponseDto checkWallet(UserCheckWalletRequestDto dto) throws Exception {
    if (userRepository.existsByWallet(dto.getWallet())){
      return UserCheckWalletResponseDto.from("이미 존재하는 지갑 주소입니다.", false);
    }
    return UserCheckWalletResponseDto.from("사용 가능한 지갑 주소입니다.", true);
  }
}
