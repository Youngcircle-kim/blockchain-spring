package com.uxm.blockchain.domain.music.service;

import com.uxm.blockchain.domain.music.dto.request.CheckMusicChartRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicSearchRequest;
import com.uxm.blockchain.domain.music.dto.response.CheckMusicChartResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicInfoResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicSearchResponse;
import java.util.List;

public interface MusicService {

  List<MusicSearchResponse> musicSearch(MusicSearchRequest dto) throws Exception;

  List<CheckMusicChartResponse> checkMusicChart(CheckMusicChartRequest dto) throws Exception;

  MusicInfoResponse musicInfo(Long id) throws Exception;
}