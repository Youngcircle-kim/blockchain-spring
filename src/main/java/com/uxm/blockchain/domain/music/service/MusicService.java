package com.uxm.blockchain.domain.music.service;

import com.uxm.blockchain.common.Enum.Genre;
import com.uxm.blockchain.domain.music.dto.request.CheckMusicChartRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicSearchRequest;
import com.uxm.blockchain.domain.music.dto.response.CheckMusicChartResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicInfoResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicSearchResponse;
import java.util.List;

public interface MusicService {

  List<MusicSearchResponse> musicSearch(MusicSearchRequest dto) throws Exception;

  List<CheckMusicChartResponse> checkMusicChart(Genre genre) throws Exception;

  MusicInfoResponse musicInfo(Long id) throws Exception;
}