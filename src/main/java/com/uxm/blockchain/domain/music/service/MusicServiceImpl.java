package com.uxm.blockchain.domain.music.service;

import com.uxm.blockchain.common.Enum.Genre;
import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.domain.music.dto.request.MusicSearchRequest;
import com.uxm.blockchain.domain.music.dto.response.CheckMusicChartResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicInfoResponse;
import com.uxm.blockchain.domain.music.dto.response.MusicSearchResponse;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONArray;
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
  private final UserRepository userRepository;
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
      IPFS ipfs = this.ipfsConfig.IPFS();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] imageByte = ipfs.cat(multihash);
      JSONObject songInfo = new JSONObject(new String(imageByte)).getJSONObject("songInfo");
      String imageCid = songInfo.getString("imageCid");

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
  private String findCid(String cid1, String key) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] imageByte = ipfs.cat(multihash);
      JSONObject songInfo = new JSONObject(new String(imageByte)).getJSONObject("songInfo");
      return new String(songInfo.getString(key));
    }catch (Exception e){
      throw new Exception("Error : communicating with the IPFS node" + e.getMessage());
    }
  }
  private JSONArray findArrayCid(String cid1, String key) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] imageByte = ipfs.cat(multihash);
      JSONObject songInfo = new JSONObject(new String(imageByte)).getJSONObject("songInfo");
      val result = songInfo.getJSONArray(key);
      log.info("result : {}", result);
      return result;
    }catch (Exception e){
      throw new Exception("Error : communicating with the IPFS node" + e.getMessage());
    }
  }
  private int findArtistIdByCid(String cid1) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] imageByte = ipfs.cat(multihash);
      JSONObject songInfo = new JSONObject(new String(imageByte)).getJSONObject("songInfo");
      return songInfo.getInt("artistId");
    }catch (Exception e){
      throw new Exception("Error : communicating with the IPFS node" + e.getMessage());
    }
  }
  private List<String> convertUserIdToName(JSONArray ja){
    List<String> lst = new ArrayList<>();
    for (int i = 0; i < ja.length(); i++){
      lst.add(this.userRepository.findById(ja.getLong(i)).get().getName());
    }
    return lst;
  }

  @Override
  public List<CheckMusicChartResponse> checkMusicChart(Genre genre) throws Exception {
    val result = this.musicRepository.findAllByGenre(genre);
    if (result.isEmpty()){
      throw new Exception("음원 리스트 조회 실패");
    }
    List<CheckMusicChartResponse> list = new ArrayList<>();
    for (int i = 0; i < result.size(); i++){
      list.add(CheckMusicChartResponse
          .builder()
          .id(result.get(i).getId())
          .title(result.get(i).getTitle())
          .image(findImageCid(result.get(i).getCid1()))
          .artist(result.get(i).getArtist())
          .build());
    }
    return list;
  }

  @Override
  public MusicInfoResponse musicInfo(Long id) throws Exception {
    val result = this.musicRepository.findById(id).orElseThrow(() -> new Exception("존재하지 않는 음원입니다."));
    return MusicInfoResponse
        .builder()
        .id(id)
        .title(result.getTitle())
        .artistId(findArtistIdByCid(result.getCid1()))
        .album(findCid(result.getCid1(), "album"))
        .image(findImageCid(result.getCid1()))
        .lyrics(findCid(result.getCid1(), "lyrics"))
        .genre(result.getGenre())
        .composerId(findArrayCid(result.getCid1(), "composerId"))
        .songWriter(convertUserIdToName(findArrayCid(result.getCid1(), "composerId")))
        .songWriterId(findArrayCid(result.getCid1(), "songWriterId"))
        .songWriter(convertUserIdToName(findArrayCid(result.getCid1(), "songWriterId")))
        .build();
  }
}
