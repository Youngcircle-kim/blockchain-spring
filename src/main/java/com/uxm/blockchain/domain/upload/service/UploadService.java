package com.uxm.blockchain.domain.upload.service;

import com.uxm.blockchain.domain.upload.dto.request.CheckMusicDuplicatedRequest;
import com.uxm.blockchain.domain.upload.dto.request.UploadMetadataRequest;
import com.uxm.blockchain.domain.upload.dto.request.UploadMusicRequest;
import com.uxm.blockchain.domain.upload.dto.response.CheckMusicDuplicatedResponse;
import com.uxm.blockchain.domain.upload.dto.response.DeleteMusicResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMetadataResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMusicInfoResponse;
import com.uxm.blockchain.domain.upload.dto.response.UploadMusicResponse;
import java.util.List;


public interface UploadService {
  List<UploadMusicInfoResponse> uploadMusicInfo() throws Exception;
  UploadMetadataResponse uploadMetadata(UploadMetadataRequest dto)throws Exception;
  CheckMusicDuplicatedResponse checkMusicDuplicated(CheckMusicDuplicatedRequest dto) throws Exception;
  DeleteMusicResponse deleteMusic(long id) throws Exception;
  UploadMusicResponse uploadMusic(UploadMusicRequest dto) throws Exception;
}
