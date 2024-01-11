package com.uxm.blockchain.domain.music.repository;

import com.uxm.blockchain.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

}
