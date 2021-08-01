package com.example.snowball.domain.accesstoken;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
  Optional<AccessToken> findByTokenAndIsActiveAndType(
      String token, boolean isActive, AccessTokenType type);

  List<AccessToken> findAllByUserIdAndIsActiveAndType(
      Long userId, boolean isActive, AccessTokenType type);

  AccessToken findTopByUserIdAndIsActive(long userId, boolean isActive);

}
