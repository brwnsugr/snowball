package com.example.snowball.util;

import com.google.api.client.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private String secretKey;
  private long validityInMilliseconds;
  private String refreshSecretKey;
  private long refreshValidityInMilliseconds;

  public JwtTokenProvider(
      @Value("${spring.security.jwt.access.token.secret-key}") String secretKey,
      @Value("${spring.security.jwt.access.token.expire-length}") long validityInMilliseconds,
      @Value("${spring.security.jwt.refresh.token.secret-key}") String refreshSecretKey,
      @Value("${spring.security.jwt.refresh.token.expire-length}") long refreshValidityInMilliseconds) {
    this.secretKey =
        new String(
            Base64.encodeBase64(secretKey.getBytes(StandardCharsets.UTF_8)),
            StandardCharsets.UTF_8);
    this.validityInMilliseconds = validityInMilliseconds;
    this.refreshSecretKey =
        new String(
            Base64.encodeBase64(refreshSecretKey.getBytes(StandardCharsets.UTF_8)),
            StandardCharsets.UTF_8);
    this.refreshValidityInMilliseconds = refreshValidityInMilliseconds;
  }

  public String parseTokenString(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String getOriginalToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken;
    }
    return null;
  }

  private String createToken(
      String subject,
      Map<String,Object> addClaims,
      String secretKey,
      long validityInMilliseconds) {
    Claims claims = Jwts.claims().setSubject(subject);
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    JwtBuilder jwtBuilder = Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey);
    for(Entry<String, Object> entry: addClaims.entrySet()) {
      jwtBuilder.claim(entry.getKey(), entry.getValue());
    }
    return jwtBuilder.compact();
  }

  public String createToken(String subject, Map<String,Object> addClaims) {
    return this.createToken(subject, addClaims, this.secretKey, this.validityInMilliseconds);
  }

  public String createRefreshToken(String subject, Map<String, Object> addClaims) {
    return this.createToken(subject, addClaims, this.refreshSecretKey, this.refreshValidityInMilliseconds);
  }

  private Claims decode(String jwt, String secretKey) {
    Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
    return claims;
  }

  public Claims decode(String jwt, String publicKeyString, String publicKeyExponent)
    throws InvalidKeySpecException, NoSuchAlgorithmException {
    PublicKey publicKey = getPublicKey(publicKeyString, publicKeyExponent);
    Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwt).getBody();
    return claims;
  }

  public Claims decodeToken(String jwt) {
    return decode(jwt, this.secretKey);
  }

  public Claims decodeRefreshToken(String jwt) {
    return decode(jwt, this.refreshSecretKey);
  }

  public boolean validate(String jwt, String secretKey) {
    Claims claims = decode(jwt, secretKey);
    Date current = new Date();
    if (current.compareTo(claims.getExpiration()) < 0) {
      return true;
    }
    return false;
  }

  public boolean validate(String jwt, String publicKeyString, String publicKeyExponent)
    throws InvalidKeySpecException, NoSuchAlgorithmException {
    Claims claims = decode(jwt, publicKeyString, publicKeyExponent);
    Date current = new Date();
    if (current.compareTo(claims.getExpiration()) < 0) {
      return true;
    }
    return false;
  }

  public boolean validate(String jwt) {
    Claims claims = decodeToken(jwt);
    Date current = new Date();
    if (current.compareTo(claims.getExpiration()) < 0) {
      return true;
    }
    return false;
  }

  public boolean validate(Claims claims) {
    Date current = new Date();
    if (current.compareTo(claims.getExpiration())<0) {
      return true;
    }
    return false;
  }

  public boolean validateRefreshToken(String jwt) {
    Claims claims = decodeRefreshToken(jwt);
    Date current = new Date();
    if (current.compareTo(claims.getExpiration()) < 0) {
      return true;
    }
    return false;
  }

  private static PublicKey getPublicKey(String publicKeyString, String publicKeyExponent)
    throws NoSuchAlgorithmException, InvalidKeySpecException {
    BigInteger n = new BigInteger(1, Decoders.BASE64URL.decode(publicKeyString));
    BigInteger e = new BigInteger(1, Decoders.BASE64URL.decode(publicKeyExponent));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    KeySpec publicKeySpec = new RSAPublicKeySpec(n,e);
    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
    return publicKey;
  }
}
