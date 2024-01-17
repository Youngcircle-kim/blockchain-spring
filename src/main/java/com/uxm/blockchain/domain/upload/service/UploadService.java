package com.uxm.blockchain.domain.upload.service;

import com.uxm.blockchain.domain.upload.dto.request.CheckMusicDuplicatedRequest;
import com.uxm.blockchain.domain.upload.dto.request.UploadMetadataRequest;
import com.uxm.blockchain.domain.upload.dto.response.CheckMusicDuplicatedResponse;
import com.uxm.blockchain.domain.upload.dto.response.DeleteMusicResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMetadataResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMusicInfoResponse;
import java.util.List;


public interface UploadService {
  public List<UploadMusicInfoResponse> uploadMusicInfo() throws Exception;
  public UploadMetadataResponse uploadMetadata(UploadMetadataRequest dto)throws Exception;
  public CheckMusicDuplicatedResponse checkMusicDuplicated(CheckMusicDuplicatedRequest dto) throws Exception;
  public DeleteMusicResponse deleteMusic(long id) throws Exception;
}
