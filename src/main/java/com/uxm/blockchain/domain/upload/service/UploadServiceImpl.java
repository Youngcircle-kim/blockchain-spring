package com.uxm.blockchain.domain.upload.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.upload.dto.request.UploadMetadataRequest;
import com.uxm.blockchain.domain.upload.dto.response.UploadMetadataResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMusicInfoResponse;
import com.uxm.blockchain.domain.upload.model.Metadata;
import com.uxm.blockchain.domain.upload.model.SongInfo;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import com.uxm.blockchain.domain.user.repository.mapping.UserInfoMapping;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multihash.Multihash;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UploadServiceImpl implements UploadService {
  private final IPFSConfig ipfsConfig;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  @Override
  public List<UploadMusicInfoResponse> uploadMusicInfo() throws Exception{
    UserDetails userDetails = getUserInfo();
    Optional<UserInfoMapping> users = this.userRepository.findInfoByEmail(
        userDetails.getUsername());

    val uploadMusics = this.musicRepository.findAllById(
            users.get().getId()).stream()
        .map(UploadMusicInfoResponse::from)
        .toList();

    for (UploadMusicInfoResponse r : uploadMusics){
      r.setImage(findImageByCid(r.getImage()));
      r.setAlbum(findAlbumNameByCid(r.getImage()));
    }

    return uploadMusics;
  }

  @Override
  public UploadMetadataResponse uploadMetadata(UploadMetadataRequest dto) throws Exception {
    try{
      UserDetails userDetails = getUserInfo();
      Optional<User> user = this.userRepository.findByEmail(userDetails.getUsername());
      if (!user.isPresent()) {
        throw new Exception("유저가 없습니다.");
      }
      String imgCid = addImageFileOnIPFS(dto.getImage().getOriginalFilename(), dto.getImage().getBytes());
      SongInfo songInfo = SongInfo.builder()
          .title(dto.getTitle())
          .artist(dto.getArtist())
          .artistId(dto.getArtistId())
          .album(dto.getAlbum())
          .genre(dto.getGenre())
          .lyrics(dto.getLyrics())
          .imageCid(imgCid)
          .composerId(dto.getComposerId())
          .songWriterId(dto.getSongWriterId())
          .build();
      Metadata metadata = Metadata.builder()
          .uploaderId(user.get().getId())
          .uploadTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()))
          .songInfo(songInfo)
          .build();
      String cid = addMetaInIPFS(metadata);
      return UploadMetadataResponse
          .builder()
          .cid(cid)
          .build();
    } catch (Exception e){
      throw new Exception("Error : Not uploading metadata " + e.getMessage());
    }
  }


  private String findImageByCid(String cid1) throws Exception {
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
      throw new Exception("Error : Not communicating with the IPFS node");
    }
  }

  private String findAlbumNameByCid(String cid1) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.getIpfs();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] imageByte = ipfs.cat(multihash);
      String songInfo = new JSONObject(new String(imageByte)).getString("songInfo");
      return new JSONObject(songInfo).getString("album");
    } catch (Exception e){
      throw new Exception("Error : Not communicating with the IPFS node");
    }
  }
  private String addImageFileOnIPFS(String originalName, byte[] imgBuffer) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.getIpfs();
      NamedStreamable.ByteArrayWrapper file = new ByteArrayWrapper(originalName, imgBuffer);
      MerkleNode addResult = ipfs.add(file).get(0);
      return addResult.hash.toBase58();
    } catch (Exception e){
      throw new Exception("Error : Not communicating with the IPFS node");
    }
  }

  private String addMetaInIPFS(Metadata metadata) throws Exception {
    try{
      ObjectMapper objectMapper = new ObjectMapper();
      IPFS ipfs = this.ipfsConfig.getIpfs();
      String metadataJson = objectMapper.writeValueAsString(metadata);
      NamedStreamable.ByteArrayWrapper meta = new ByteArrayWrapper(metadataJson.getBytes());
      MerkleNode metadataCid = ipfs.add(meta).get(0);
      return metadataCid.hash.toBase58();
    } catch (Exception e){
      throw new Exception("Error : Not communicating with the IPFS node");
    }
  }

  private UserDetails getUserInfo(){
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    return (UserDetails) authentication;
  }

}
