package com.uxm.blockchain.domain.upload.service;

import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.upload.dto.request.UploadMetadataRequest;
import com.uxm.blockchain.domain.upload.dto.response.UploadMetadataResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMusicInfoResponse;
import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import java.util.Base64;
import java.util.List;
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
  @Override
  public List<UploadMusicInfoResponse> uploadMusicInfo() throws Exception{
    UserDetails userDetails = getUserInfo();

    val uploadMusics = this.musicRepository.findAllByEmail(
            userDetails.getUsername()).stream()
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
    return null;
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

  private UserDetails getUserInfo(){
    val authentication = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();

    return (UserDetails) authentication;
  }

}
