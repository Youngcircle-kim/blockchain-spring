package com.uxm.blockchain.config;

import io.ipfs.api.IPFS;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Slf4j
public class IPFSConfig {

  @Value("${IPFS.URL}")
  private String IPFS_URL;

  public IPFS IPFS() {
    return new IPFS(IPFS_URL);
  }
}
