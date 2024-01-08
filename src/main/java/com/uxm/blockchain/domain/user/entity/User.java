package com.uxm.blockchain.domain.user.entity;

import com.uxm.blockchain.common.Enum.Type;
import com.uxm.blockchain.domain.music.entity.Music;
import com.uxm.blockchain.domain.nft.entity.Nft;
import com.uxm.blockchain.domain.purchase.entity.Purchase;
import com.uxm.blockchain.domain.user_nft.entity.User_nft;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String wallet;

    @OneToMany(mappedBy = "user")
    private List<Music> musics = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Nft> nfts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<User_nft> userNfts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Builder()
    public User(String name, String email, Type type, String password, String nickname, String wallet){
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.type = type;
        this.password = password;
        this.wallet = wallet;
    }

    public void updateUserInfo(String name, String nickname, String password){
        this.name = name;
        this.nickname = nickname;
        this.password = password;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return name;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
