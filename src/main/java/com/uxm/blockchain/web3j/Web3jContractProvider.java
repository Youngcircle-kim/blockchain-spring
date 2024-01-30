package com.uxm.blockchain.web3j;

import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.contracts.NFT1155;
import com.uxm.blockchain.contracts.SettlementContractExtra;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;

@Component
public class Web3jContractProvider {
  public SettlementContractExtra settlementContractExtra(String contractAddress, Web3j web3j, Web3jConfig web3jConfig){
    return SettlementContractExtra.load(contractAddress, web3j, web3jConfig.credentials(), web3jConfig.gasProvider());
  }

  public NFT1155 nft1155(String contractAddress, Web3j web3j, Web3jConfig web3jConfig){
    return NFT1155.load(contractAddress, web3j, web3jConfig.credentials(), web3jConfig.gasProvider());
  }
}
