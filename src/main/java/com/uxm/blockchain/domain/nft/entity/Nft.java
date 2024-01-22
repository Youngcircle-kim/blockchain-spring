package com.uxm.blockchain.domain.nft.entity;

import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.user.entity.User;
import com.uxm.blockchain.domain.user_nft.entity.User_nft;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Nft {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String cid;

  @Column(nullable = false)
  private String contractAddress;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "music_id")
  private Music music;

  @OneToMany(mappedBy = "nft")
  private List<User_nft> userNfts = new ArrayList<>();

  @Builder()
  public Nft(String cid, String contractAddress, User user, Music music){
    this.cid = cid;
    this.contractAddress = contractAddress;
    this.user = user;
    this.music = music;
  }
}
