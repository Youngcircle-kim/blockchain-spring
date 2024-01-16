package com.uxm.blockchain.domain.music.service;

import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.domain.music.dto.request.CheckMusicChartRequest;
import com.uxm.blockchain.domain.music.dto.request.MusicSearchRequest;
import com.uxm.blockchain.domain.music.dto.response.CheckMusicChartResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicInfoResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicSearchResponse;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicServiceImpl implements MusicService{

  private final IPFSConfig ipfsConfig;
  private final MusicRepository musicRepository;
  private final PurchaseRepository purchaseRepository;

  @Override
  public List<MusicSearchResponse> musicSearch(MusicSearchRequest dto) throws Exception {
    val search = dto.getSearch();
    List<MusicSearchResponse> result = new ArrayList<>();
    String[] words = search.split(" ");

    for (String word : words) {
      findMusicByTitle(word).ifPresent(result::addAll);
      findMusicByArtist(word).ifPresent(result::addAll);
    }
    if(result.isEmpty()) {
      throw new Exception("해당하는 노래가 없습니다.");
    }

    for (MusicSearchResponse m : result){
      m.setImage(findImageCid(m.getImage()));
    }
    return result;
  }
  private String findImageCid(String cid1) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.getIpfs();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] imageByte = ipfs.cat(multihash);
      String songInfo = new JSONObject(new String(imageByte)).getString("songInfo");
      String imageCid = new JSONObject(songInfo).getString("imageCid");

      Multihash imageFilePointer = Multihash.fromBase58(imageCid);
      byte[] fileContents = ipfs.cat(imageFilePointer);

      return Base64.getEncoder().encodeToString(fileContents);
    }catch (Exception e){
      throw new Exception("Error : communicating with the IPFS node");
    }
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
  public List<CheckMusicChartResponse> checkMusicChart(CheckMusicChartRequest dto) throws Exception {
    val result = this.musicRepository.findAllByGenre(dto.getGenre());
    if (result.isEmpty()){
      throw new Exception("음원 리스트 조회 실패");
    }
    for (Music m : result){
      m.setCid1(findImageCid(m.getCid1()));
    }
    return result.stream()
        .map(CheckMusicChartResponse::from)
        .collect(Collectors.toList());
  }

  @Override
  public MusicInfoResponse musicInfo(Long id) throws Exception {
    val result = this.musicRepository.findById(id).orElseThrow(() -> new Exception("존재하지 않는 음원입니다."));
    return MusicInfoResponse.from(result);
  }
}
