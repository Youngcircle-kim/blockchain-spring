package com.uxm.blockchain.domain.user.repository;

import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.mapping.UserInfoMapping;
import com.uxm.blockchain.domain.user.repository.mapping.UserSignUpMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByNickname(String nickname);
  boolean existsByWallet(String wallet);
  Optional<UserSignUpMapping> findSignUpByEmail(String email);
  Optional<UserInfoMapping> findInfoByEmail(String email);

  List<User> findAllByEmail(String email);

  Optional<User> findByWallet(String wallet);
}
