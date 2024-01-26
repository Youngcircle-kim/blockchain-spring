package com.uxm.blockchain.config;

import com.uxm.blockchain.contracts.NFT1155;
import com.uxm.blockchain.contracts.SettlementContract;
import com.uxm.blockchain.contracts.SettlementContractExtra;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.StaticGasProvider;

@Configuration
public class Web3jConfig {
  @Value("${infura.API_URL}")
  private String INFURA_API_URL;

  @Value("${metamask.PRIVATE_KEY}")
  private String PRIVATE_KEY;

  @Value("${metamask.NFT_CONTRACT_ADDRESS}")
  private String NFT_CONTRACT_ADDRESS;

  @Value("${metamask.Settlement_CONTRACT_ADDRESS}")
  private String SETTLEMENT_CONTRACT_ADDRESS;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(INFURA_API_URL));
  }

  @Bean
  public Credentials credentials() {
    BigInteger privateKeyInBT = new BigInteger(PRIVATE_KEY, 16);
    return Credentials.create(ECKeyPair.create(privateKeyInBT));
  }
  //SettlementContract의 수정 버전이 Extra라 기존 Settlement Contract는 사용 안함.
  @Bean
  public SettlementContract settlementContract(){
    return SettlementContract.load(SETTLEMENT_CONTRACT_ADDRESS, web3j(), credentials(), gasProvider());
  }

  @Bean
  public SettlementContractExtra settlementContractExtra(){
    return SettlementContractExtra.load(SETTLEMENT_CONTRACT_ADDRESS, web3j(), credentials(), gasProvider());
  }
  @Bean
  public NFT1155 nft(){
    return NFT1155.load(NFT_CONTRACT_ADDRESS, web3j(), credentials(), gasProvider());
  }

  private StaticGasProvider gasProvider(){
    BigInteger gasPrice = Contract.GAS_PRICE;
    BigInteger gasLimit = Contract.GAS_LIMIT;
    return new StaticGasProvider(gasPrice, gasLimit);
  }

}
