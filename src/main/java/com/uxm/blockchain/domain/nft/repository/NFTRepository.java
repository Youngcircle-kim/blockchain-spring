package com.uxm.blockchain.domain.nft.repository;

import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NFTRepository extends JpaRepository<Nft, Long> {
  Optional<Nft> findByUserAndMusic(User user, Music music);
}
