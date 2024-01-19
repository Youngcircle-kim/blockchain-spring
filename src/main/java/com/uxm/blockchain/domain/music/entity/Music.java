package com.uxm.blockchain.domain.music.entity;

import com.uxm.blockchain.common.Enum.Genre;
import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.purchase.entity.Purchase;
import com.uxm.blockchain.domain.user.entity.User;
import jakarta.persistence.*;
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
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(unique = true, nullable = false)
    private String cid1;

    @Column(unique = true, nullable = false)
    private String cid2;

    @Column(unique = true, nullable = false)
    private String cid3;

    @Column(nullable = false)
    private String sha1;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "music")
    private List<Nft> nfts = new ArrayList<>();

    @OneToMany(mappedBy = "music")
    private List<Purchase> purchases = new ArrayList<>();

    @Builder()
    public Music(String title, String artist, String cid1, String cid2, String cid3, String sha1, String address, Genre genre, User user){
        this.title = title;
        this.artist = artist;
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.cid3 = cid3;
        this.sha1 = sha1;
        this.address = address;
        this.genre = genre;
        this.user = user;
    }

  public void setCid1(String cid1) {
        this.cid1 = cid1;
  }

  public void setCid2(String cid2) {this.cid2 = cid2;}
}
