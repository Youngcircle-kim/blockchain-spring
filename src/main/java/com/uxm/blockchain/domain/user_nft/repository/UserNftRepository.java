package com.uxm.blockchain.domain.user_nft.repository;

import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user_nft.entity.User_nft;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNftRepository extends JpaRepository<User_nft, Long> {
  Optional<User_nft> findByUserAndId(User user, long id);
  Optional<User_nft> findById(long id);
}
