package com.uxm.blockchain.domain.music.repository;

import com.uxm.blockchain.common.Enum.Genre;
import com.uxm.blockchain.domain.music.entity.Music;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

  Optional<Music> findById(Long id);

  Boolean existsByTitle(String title);

  List<Music> findAllByTitle(String title);

  Boolean existsByArtist(String artist);

  List<Music> findAllByArtist(String artist);
  List<Music> findAllByGenre(Genre genre);
  List<Music> findAllById(Long id);
  boolean existsBySha1(String sha1);
  void deleteById(Long id);

}
