package ar.com.rodrigoperdomo.server.utils;

import static ar.com.rodrigoperdomo.server.utils.Constantes.EMAIL_KEY;
import static ar.com.rodrigoperdomo.server.utils.Constantes.SECRET_KEY_ENV;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  private Key key;

  public JwtUtils() {
    String secretKey = System.getenv(SECRET_KEY_ENV);
    if (secretKey != null) {
      key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
  }

  /**
   * Método para generar token de JWT
   *
   * @param username
   * @return Token en formato String
   */
  public String generateToken(String username, String email) {
    long hourToken = 1000L * 60 * 60 * 8;
    Map<String, Object> claims = new HashMap<>();
    claims.put(EMAIL_KEY, email);
    return Jwts.builder()
        .subject(username)
        .claims(claims)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + hourToken))
        .signWith(key)
        .compact();
  }

  /**
   * Extraer el username del token
   *
   * @param token
   * @return username en formato String
   */
  public String extractUsername(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  /**
   * Metodo que valida el token
   *
   * @param token
   * @param username
   * @return boolean con resultado de validacion
   */
  public boolean isTokenValid(String token, String username) {
    return extractUsername(token).equals(username) && !isTokenExpired(token);
  }

  /**
   * Valida si el token ya expiró
   *
   * @param token
   * @return boolean con el resultado de la validacion
   */
  private boolean isTokenExpired(String token) {
    Date expiration =
        Jwts.parser()
            .verifyWith((SecretKey) key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
    return expiration.before(new Date());
  }
}
