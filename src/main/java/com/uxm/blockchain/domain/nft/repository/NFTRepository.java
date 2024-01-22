package com.uxm.blockchain.domain.nft.repository;

import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NFTRepository extends JpaRepository<Nft, Long> {
  Optional<Nft> findByUserAndMusic(User user, Music music);
  @Query("SELECT n FROM Nft n JOIN User_nft un ON n.id = un.nft.id WHERE n.music.id = :musicId")
  List<Nft> findByMusicId(@Param("musicId") Long musicId);

  @Query("SELECT n FROM Nft n JOIN User_nft un ON n.id = un.nft.id WHERE un.is_sale = true")
  List<Nft> findOnSale();
}
