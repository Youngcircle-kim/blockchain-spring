package com.uxm.blockchain.domain.purchase.service;

import com.uxm.blockchain.config.Web3jConfig;
import com.uxm.blockchain.contracts.SettlementContract;
import com.uxm.blockchain.contracts.SettlementContract.LogBuyerInfoEventResponse;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.music.repository.MusicRepository;
import com.uxm.blockchain.domain.purchase.dto.response.MusicPurchaseResponse;
import com.uxm.blockchain.domain.purchase.entity.Purchase;
import com.uxm.blockchain.domain.purchase.repository.PurchaseRepository;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.StaticArray2;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseService {
  private final PurchaseRepository purchaseRepository;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  private final Web3jConfig web3jConfig;

  public MusicPurchaseResponse purchaseMusic(long musicId, String hash) throws Exception {
    try{
      String email = getUserInfo().getUsername();
      Optional<User> user = this.userRepository.findByEmail(email);
      if (user.isEmpty()) throw new Exception("존재하지 않는 유저입니다.");
      long userId = user.get().getId();

      Web3j web3j = web3jConfig.web3j();
      SettlementContract contract = web3jConfig.settlementContract();
      Optional<TransactionReceipt> transactionReceipt = web3j.ethGetTransactionReceipt(hash).send()
          .getTransactionReceipt();

      if (transactionReceipt.isEmpty()) throw new Exception("Receipt 없음");
      TransactionReceipt receipt = transactionReceipt.get();
      LogBuyerInfoEventResponse logBuyerInfoEventFromLog = contract.getLogBuyerInfoEventFromLog(
          receipt.getLogs().get(0));

      String buyer = logBuyerInfoEventFromLog.buyer;
      List<byte[]> songCidBytes = logBuyerInfoEventFromLog.songCid;

      StringBuilder songCidBuilder = new StringBuilder();
      for (byte[] bytes : songCidBytes) {
        songCidBuilder.append(new String(bytes, StandardCharsets.UTF_8));
      }
      String songCid = songCidBuilder.toString();

      Optional<User> buyerUser = this.userRepository.findByWallet(buyer);
      Optional<Music> boughtMusic = this.musicRepository.findByCid1(songCid);

      if (buyerUser.isEmpty() || boughtMusic.isEmpty()) throw new Exception("존재하지 않는 유저나 음악입니다.");

      Long user_id = buyerUser.get().getId();
      Long music_id = boughtMusic.get().getId();
      if (user_id != userId || musicId != music_id){
          throw new Exception("권한이 없습니다.");
      }
      Purchase purchase = this.purchaseRepository.save(Purchase.builder()
          .user(buyerUser.get())
          .music(boughtMusic.get())
          .build());
      return MusicPurchaseResponse.builder()
          .id(purchase.getId())
          .build();
    } catch (Exception e){
      throw new Exception("음원 결제 실패");
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
