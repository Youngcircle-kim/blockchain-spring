package com.uxm.blockchain.config;

import io.ipfs.api.IPFS;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Getter
public class IPFSConfig {


  private final IPFS ipfs;
  public IPFSConfig() {
    ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
  }
}
