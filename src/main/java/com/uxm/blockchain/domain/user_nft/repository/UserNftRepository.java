package com.uxm.blockchain.domain.user_nft.repository;

import com.uxm.blockchain.domain.user_nft.entity.User_nft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNftRepository extends JpaRepository<User_nft, Long> {

}
