package com.uxm.blockchain.domain.user.service;

import com.uxm.blockchain.domain.user.dto.response.UserSignUpResponseDto;
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
    System.out.println("1");
    if (userRepository.findByEmail(dto.getEmail()).isPresent()){
      System.out.println("2");
      return UserSignUpResponseDto.from("회원가입 실패 - 이미 존재하는 이메일입니다.");
    }
    if(userRepository.findByNickname(dto.getNickname()).isPresent()){
      System.out.println("3");
      return UserSignUpResponseDto.from("회원가입 실패 - 이미 존재하는 닉네임입니다.");
    }
    System.out.println("4");
    User user = userRepository.save(dto.toEntity());
    user.encodePassword(passwordEncoder);
    System.out.println("5");
    return UserSignUpResponseDto.from(userRepository.findAllByEmail(user.getEmail()), "회원가입 성공");
  }
}
