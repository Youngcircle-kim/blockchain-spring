package com.uxm.blockchain.domain.music.repository.mapping;

import com.uxm.blockchain.common.Enum.Genre;

public interface MusicChartMapping {
  Long getId();
  Long getUserId();
  String getTitle();
  Genre getGenre();
  String getArtist();
  String getCid1();
}
