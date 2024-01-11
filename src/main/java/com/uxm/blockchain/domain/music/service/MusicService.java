package com.uxm.blockchain.domain.music.service;

import com.uxm.blockchain.domain.music.dto.request.CheckMusicChartRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicInfoRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicSearchRequest;
import com.uxm.blockchain.domain.music.dto.response.CheckMusicChartResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicInfoResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicSearchResponse;

public interface MusicService {

  public MusicSearchResponse musicSearch(MusicSearchRequest dto) throws Exception;

  public CheckMusicChartResponse checkMusicChart(CheckMusicChartRequest dto) throws Exception;

  public MusicInfoResponse musicInfo(MusicInfoRequest dto) throws Exception;
}