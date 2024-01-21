package com.uxm.blockchain.domain.purchase.repository;

import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.purchase.entity.Purchase;
import com.uxm.blockchain.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
  List<Purchase> findAllByUser(User user);
  Optional<Purchase> findByUserAndMusic(User user, Music music);
}
