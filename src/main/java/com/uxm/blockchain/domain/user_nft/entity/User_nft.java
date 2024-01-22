package com.uxm.blockchain.domain.user_nft.entity;

import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User_nft {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Boolean is_sale;

  @Column(nullable = true)
  private String sell_tx;

  @Column(nullable = true)
  private  String purchase_tx;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nft_id")
  private Nft nft;

  @Builder()
  public User_nft(Boolean is_sale, String sell_tx, String purchase_tx, User user, Nft nft){
    this.is_sale = is_sale;
    this.sell_tx = sell_tx;
    this.purchase_tx = purchase_tx;
    this.user = user;
    this.nft = nft;
  }
}
