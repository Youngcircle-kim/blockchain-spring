package com.uxm.blockchain.domain.music.service;

import com.uxm.blockchain.domain.music.dto.request.CheckMusicChartRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicInfoRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicSearchRequest;
import com.uxm.blockchain.domain.music.dto.response.CheckMusicChartResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicInfoResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicSearchResponse;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicServiceImpl implements MusicService{

  private final MusicRepository musicRepository;

  @Override
  public List<MusicSearchResponse> musicSearch(MusicSearchRequest dto) throws Exception {
    val search = dto.getSearch();
    List<MusicSearchResponse> result = new ArrayList<>();
    String[] words = search.split(" ");

    for (String word : words) {
      findMusicByTitle(word).ifPresent(result::addAll);
      findMusicByArtist(word).ifPresent(result::addAll);
    }
    if(result.isEmpty())
      throw new Exception("해당하는 노래가 없습니다.");
    return result;
  }

  private Optional<List<MusicSearchResponse>> findMusicByTitle(String title) {
    if (this.musicRepository.existsByTitle(title)) {
      return Optional.of(this.musicRepository.findAllByTitle(title)
          .stream()
          .map(MusicSearchResponse::from)
          .collect(Collectors.toList()));
    }
    return Optional.empty();
  }

  private Optional<List<MusicSearchResponse>> findMusicByArtist(String artist) {
    if (this.musicRepository.existsByArtist(artist)) {
      return Optional.of(this.musicRepository.findAllByArtist(artist)
          .stream()
          .map(MusicSearchResponse::from)
          .collect(Collectors.toList()));
    }
    return Optional.empty();
  }

  @Override
  public CheckMusicChartResponse checkMusicChart(CheckMusicChartRequest dto) throws Exception {
    return null;
  }

  @Override
  public MusicInfoResponse musicInfo(Long id) throws Exception {
    val result = this.musicRepository.findById(id).orElseThrow(() -> new Exception("존재하지 않는 음원입니다."));
    return MusicInfoResponse.from(result);
  }
}
