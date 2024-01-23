package com.uxm.blockchain.config;

import com.uxm.blockchain.filter.JwtAuthenticationFilter;
import com.uxm.blockchain.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
  private final JwtTokenProvider jwtTokenProvider;
  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors();
    httpSecurity
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/auth/**").permitAll()
        .requestMatchers("/swagger-ui/**").permitAll()
        .requestMatchers("/v3/api-docs/**").permitAll()
        .requestMatchers("/api/v1/user/**").hasRole("USER")
        .requestMatchers("/api/v1/music/**").hasRole("USER")
        .requestMatchers("/api/v1/nft/**").hasRole("USER")
        .requestMatchers("/api/v1/upload/**").hasRole("USER")
        .requestMatchers("/api/v1/purchase/**").hasRole("USER")
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

}
