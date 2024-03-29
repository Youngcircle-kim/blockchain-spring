package com.uxm.blockchain.domain.upload.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uxm.blockchain.common.Enum.Type;
import com.uxm.blockchain.config.IPFSConfig;
import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.upload.dto.request.CheckMusicDuplicatedRequest;
import com.uxm.blockchain.domain.upload.dto.request.UploadMetadataRequest;
import com.uxm.blockchain.domain.upload.dto.request.UploadMusicRequest;
import com.uxm.blockchain.domain.upload.dto.response.CheckMusicDuplicatedResponse;
import com.uxm.blockchain.domain.upload.dto.response.DeleteMusicResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMetadataResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMusicInfoResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMusicResponse;
import com.uxm.blockchain.domain.upload.model.Metadata;
import com.uxm.blockchain.domain.upload.model.PayProperty;
import com.uxm.blockchain.domain.upload.model.RightHolder;
import com.uxm.blockchain.domain.upload.model.SongInfo;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multibase.Base58;
import io.ipfs.multihash.Multihash;
import jakarta.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.bouncycastle.util.encoders.UTF8;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Numeric;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UploadServiceImpl implements UploadService {

  @Value("${IPFS_ENC_KEY}")
  private String key;

  @Value("${IPFS_ENC_IV}")
  private String iv;

  private final IPFSConfig ipfsConfig;
  private final Web3jConfig web3jConfig;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  @Override
  public List<UploadMusicInfoResponse> uploadMusicInfo() throws Exception{
    UserDetails userDetails = getUserInfo();
    Optional<User> user = this.userRepository.findByEmail(
        userDetails.getUsername());

    val musics = this.musicRepository.findAllByUser(
            user.get());
    List<UploadMusicInfoResponse> uploadMusics = musics.stream()
        .map(UploadMusicInfoResponse::from)
        .toList();

    for (int i = 0; i < musics.size(); i++) {
      String base64Image = Base64.getEncoder().encodeToString(findImageByCid(musics.get(i).getCid3()));
      uploadMusics.get(i).setImage(base64Image);
      uploadMusics.get(i).setAlbum(findAlbumNameByCid(musics.get(i).getCid1()));
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

  @Override
  public CheckMusicDuplicatedResponse checkMusicDuplicated(CheckMusicDuplicatedRequest dto) throws Exception {
    try{
      byte[] buffer = dto.getFile().getBytes();
      String sha1 = sha1Convert(buffer);
      boolean isDuplicated = false;
      if (this.musicRepository.existsBySha1(sha1)){
        isDuplicated = true;
      }
      return CheckMusicDuplicatedResponse
          .builder()
          .isDuplicated(isDuplicated)
          .build();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public DeleteMusicResponse deleteMusic(long id) throws Exception {
    try {
      Optional<User> user = this.userRepository.findByEmail(getUserInfo().getUsername());
      Optional<Music> music = this.musicRepository.findById(id);
      if (user.isEmpty() || music.isEmpty()) {
        throw new Exception("유저나 움억아 없습니다.");
      }
      if(!Objects.equals(user.get().getId(), music.get().getUser().getId())){
        throw new Exception("권한이 없습니다.");
      }
      this.musicRepository.deleteById(music.get().getId());

      return DeleteMusicResponse
          .builder()
          .id(music.get().getId())
          .build();

    } catch (Exception e) {
      throw new Exception("음악 삭제 도중 에러" + e.getMessage());
    }
  }

  @Override
  public UploadMusicResponse uploadMusic(UploadMusicRequest dto) throws Exception {
    try{
      Long id = this.userRepository.findByEmail(getUserInfo().getUsername()).get().getId();
      Type type = this.userRepository.findByEmail(getUserInfo().getUsername()).get().getType();
      if (type != Type.Producer){
        throw new Exception("권한이 없습니다.");
      }

      List<Long> holder = dto.getHolder();
      List<RightHolder> rightHolders = new ArrayList<>();
      List<String> addresses = new ArrayList<>();
      List<Double> proportions = new ArrayList<>();

      for(long l : holder){
        if (this.userRepository.findById(l).isEmpty()) throw new Exception("유저가 없습니다.");

        val user = this.userRepository.findById(l).get();
        rightHolders.add(RightHolder.builder()
                .userId(user.getId())
                .walletAddress(user.getWallet())
                .build());
      }

      for(int i = 0; i < rightHolders.size(); i++){
        val user = rightHolders.get(i);
        user.setProportion(dto.getRate().get(i));
        addresses.add(user.getWalletAddress());
        proportions.add(dto.getRate().get(i) * 10000);
      }

//      List<Address> addressList = new ArrayList<>();
//      for (String address : addresses) {
//        addressList.add(new Address(address));
//      }
//
//      List<Uint256> proportionList = new ArrayList<>();
//      for (Double proportion : proportions) {
//        long l = proportion.longValue();
//        proportionList.add(new Uint256(new BigInteger(Long.toString(l))));
//      }
//      //web3j hashing  and contract
//      Function function = new Function(
//          "keccak256Hash",
//          Arrays.asList(new DynamicArray<>(addressList), new DynamicArray<>(proportionList)),
//          Arrays.asList(new TypeReference<Bytes32>() {
//          })
//      );
//      String encode = FunctionEncoder.encode(function);
//
//      SettlementContractExtra contract = web3jConfig.settlementContractExtra();
//      byte[] keccak256hash = contract.keccak256Hash().send();
//      log.info("keccak256hash length : {}", keccak256hash.length);
//      log.info("keccak256hash : {}", Numeric.toHexString(Hash.sha3(keccak256hash), 0, 32, false));
//      log.info("encode : {}", Numeric.toHexString(Hash.sha3(encode.getBytes(StandardCharsets.UTF_8)), 0, 32, false));
//      if (!new String(keccak256hash, StandardCharsets.UTF_8).equals(encode)) throw new Exception("올바르지 않은 컨트랙트입니다.");

      byte[] buffer = dto.getFile().getBytes();
      String sha1 = sha1Convert(buffer);

      byte[] encrypted = encryptAES(compress(buffer));
      String cid3 = addFileInIPFS(encrypted);

      Music music = Music.builder()
          .user(this.userRepository.findByEmail(getUserInfo().getUsername()).get())
          .title(dto.getTitle())
          .genre(dto.getGenre())
          .artist(dto.getArtist())
          .cid1(dto.getCid1())
          .cid2("")
          .cid3(cid3)
          .sha1(sha1)
          .address1(dto.getAddress())
          .build();
      Music saved = this.musicRepository.save(music);

      PayProperty copyright = PayProperty.builder()
          .songId(saved.getId())
          .rightHolders(rightHolders)
          .build();
      JSONArray rightHolder = new JSONArray(rightHolders);
      JSONObject payProperty = new JSONObject()
          .put("songId",saved.getId())
              .put("rightHolders", rightHolder);
      log.info("payment : {}", new JSONObject().put("payProperty", payProperty).toString());
      String cid2 = addCopyrightIPFS(new JSONObject().put("payProperty", payProperty).toString());
      updateCid2(music.getId(), cid2);

      return UploadMusicResponse.builder()
          .id(music.getId())
          .build();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
  @Transactional
  public void updateCid2(Long id, String cid) throws Exception {
    try {
      Optional<Music> music = this.musicRepository.findById(id);
      if (music.isEmpty()) {
        throw new Exception("음악이 없습니다.");
      }
      music.get().setCid2(cid);
    } catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  private String addCopyrightIPFS(String copyright)throws Exception{
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
      NamedStreamable.ByteArrayWrapper meta = new ByteArrayWrapper(copyright.getBytes());
      MerkleNode cid = ipfs.add(meta).get(0);
      return cid.hash.toBase58();
    }catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }
  private byte[] encryptAES(byte[] gzipped)
      throws Exception {
    try{
      SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
      Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
      return cipher.doFinal(gzipped);
    } catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  private byte[] compress(byte[] buffer) throws Exception {
    try{
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
      gzipOutputStream.write(buffer);
      gzipOutputStream.close();
      return byteArrayOutputStream.toByteArray();
    }catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  private String addFileInIPFS(byte[] encrypted) throws Exception{
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
      NamedStreamable.ByteArrayWrapper file = new ByteArrayWrapper(encrypted);
      MerkleNode cid = ipfs.add(file).get(0);
      return cid.hash.toBase58();

    } catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }
  private String sha1Convert(byte[] buffer) throws NoSuchAlgorithmException {
    try{
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      md.update(buffer);
      byte[] digest = md.digest();
      return DatatypeConverter.printHexBinary(digest).toLowerCase();
    } catch (NoSuchAlgorithmException e){
      throw new NoSuchAlgorithmException("sha1 에러");
    }
  }
  private byte[] findImageByCid(String cid1) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] imageByte = ipfs.cat(multihash);
      return imageByte;
    }catch (Exception e){
      throw new Exception("Error : Not communicating with the IPFS node");
    }
  }

  private String findAlbumNameByCid(String cid1) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
      Multihash multihash = Multihash.fromBase58(cid1);
      byte[] file = ipfs.cat(multihash);
      JSONObject jsonObject = new JSONObject(new String(file, StandardCharsets.UTF_8));
      JSONObject songInfo = jsonObject.getJSONObject("songInfo");
      return songInfo.getString("album");
    } catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }
  private String addImageFileOnIPFS(String originalName, byte[] imgBuffer) throws Exception {
    try{
      IPFS ipfs = this.ipfsConfig.IPFS();
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
      IPFS ipfs = this.ipfsConfig.IPFS();
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
