package com.uxm.blockchain.domain.user.service;

import com.uxm.blockchain.domain.user.dto.response.UserCheckWalletResponse;
import com.uxm.blockchain.domain.user.dto.response.UserSignInResponse;
import com.uxm.blockchain.domain.user.dto.response.UserSignUpResponse;
import com.uxm.blockchain.domain.user.dto.resquest.UserCheckWalletRequest;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignInRequest;
import com.uxm.blockchain.domain.user.dto.resquest.UserSignUpRequest;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import com.uxm.blockchain.jwt.JwtToken;
import com.uxm.blockchain.jwt.JwtTokenProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public UserSignUpResponse signUp(UserSignUpRequest dto) {
    if (userRepository.findByEmail(dto.getEmail()).isPresent()){
      return UserSignUpResponse.from("회원가입 실패 - 이미 존재하는 이메일입니다.");
    }
    if(userRepository.findByNickname(dto.getNickname()).isPresent()){
      return UserSignUpResponse.from("회원가입 실패 - 이미 존재하는 닉네임입니다.");
    }
    User user = userRepository.save(dto.toEntity());
    user.encodePassword(passwordEncoder);
    return UserSignUpResponse.from(userRepository.findAllByEmail(user.getEmail()), "회원가입 성공");
  }

  @Override
  public UserCheckWalletResponse checkWallet(UserCheckWalletRequest dto){
    if (userRepository.existsByWallet(dto.getWallet())){
      return UserCheckWalletResponse.from("이미 존재하는 지갑 주소입니다.", false);
    }
    return UserCheckWalletResponse.from("사용 가능한 지갑 주소입니다.", true);
  }

  @Override
  public UserSignInResponse signIn(UserSignInRequest dto){
    var user = userRepository.findByEmail(dto.getEmail());

    log.info(String.valueOf(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())));

    if (passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          dto.getEmail(), dto.getPassword());

      JwtToken jwtToken = jwtTokenProvider.generateToken(authenticationToken);

      return UserSignInResponse.builder()
          .message("로그인 성공")
          .jwtToken(jwtToken)
          .build();
    }
    return UserSignInResponse.builder()
        .message("로그인 실패 - 이메일 또는 비밀번호가 일치하지 않습니다.")
        .build();
  }
}
