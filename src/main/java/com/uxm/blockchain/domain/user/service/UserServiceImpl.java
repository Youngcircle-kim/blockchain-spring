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
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import com.uxm.blockchain.jwt.JwtToken;
import com.uxm.blockchain.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    return UserSignUpResponse.from(
        userRepository.findSignUpByEmail(user.getEmail()), "회원가입 성공"
    );
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

      UsernamePasswordAuthenticationToken authenticationToken
          = new UsernamePasswordAuthenticationToken(
          dto.getEmail(), dto.getPassword()
      );
      try {
        Authentication authentication = authenticationManagerBuilder
            .getObject()
            .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

      return UserSignInResponse.builder()
          .message("로그인 성공")
          .jwtToken(jwtToken)
          .build();
      } catch (Exception e){
        log.error(e.getMessage());
      }
    }
    return UserSignInResponse.builder()
        .message("로그인 실패 - 이메일 또는 비밀번호가 일치하지 않습니다.")
        .build();
  }

  @Override
  public UserInfoResponse myInfo() throws UsernameNotFoundException{
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    UserDetails userDetails = (UserDetails) authentication;
    val user = this.userRepository.findInfoByEmail(userDetails.getUsername());
    if (user.isPresent()){
      return UserInfoResponse.from(user.get(),
          "내 정보 조회 성공");
    }
    return UserInfoResponse.from("내 정보 조회 실패");
  }

  @Override
  public UserUpdateResponse updateInfo(UserUpdateRequest dto) throws Exception{
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    UserDetails userDetails = (UserDetails) authentication;
    val userInfo = this.userRepository
        .findInfoByEmail(userDetails.getUsername())
        .get();
    User user = User.of(userInfo);

    user.updateUserInfo(dto.getName(), dto.getName(), dto.getPassword());
    user.encodePassword(passwordEncoder);
    User savedUser = userRepository.save(user);
    return UserUpdateResponse.from(savedUser);
  }

  @Override
  public UserFindOneResponse findOneInfo(UserFindOneRequest dto) throws Exception {
    User user = userRepository.findByEmail(dto.getSearch())
        .orElseThrow(() -> new Exception("존재하지 않는 유저입니다."));

    return UserFindOneResponse.from(user);
  }


}
