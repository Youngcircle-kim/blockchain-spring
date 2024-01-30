package com.uxm.blockchain.filter;

import com.uxm.blockchain.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      String token = resolveToken((HttpServletRequest) request);

      if (token != null && jwtTokenProvider.validateToken(token)) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      chain.doFilter(request, response);
    } catch (IOException e){
      throw new IOException(e.getMessage());
    } catch (ServletException e){
      throw new ServletException(e.getMessage());
    }
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
      return bearerToken.substring(7);
    }

    return null;
  }
}
