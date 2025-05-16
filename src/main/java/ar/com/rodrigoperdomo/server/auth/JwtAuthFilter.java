package ar.com.rodrigoperdomo.server.auth;

import static ar.com.rodrigoperdomo.server.utils.Constantes.AUTHORIZATION;
import static ar.com.rodrigoperdomo.server.utils.Constantes.BREARER_KEY;

import ar.com.rodrigoperdomo.server.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;

  public JwtAuthFilter(JwtUtils jwtUtil) {
    this.jwtUtils = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader(AUTHORIZATION);
    if (authHeader != null && authHeader.startsWith(BREARER_KEY)) {
      String token = authHeader.substring(7);
      String username = jwtUtils.extractUsername(token);
      if (username != null && jwtUtils.isTokenValid(token, username)) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
      }
    }
    filterChain.doFilter(request, response);
  }
}
