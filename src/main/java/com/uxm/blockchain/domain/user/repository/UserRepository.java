package com.uxm.blockchain.domain.user.repository;

import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.mapping.UserSignUpMapping;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByNickname(String nickname);
  Optional<User> findByWallet(String wallet);
  Optional<UserSignUpMapping> findAllByEmail(String email);
}
