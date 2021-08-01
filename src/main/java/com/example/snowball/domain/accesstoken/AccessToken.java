package com.example.snowball.domain.accesstoken;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
    name = "access_tokens",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"token"})}
)
public class AccessToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(length = 11)
  private Long id;

  @Column
  private Long userId;

  @Column(unique = true)
  private String token;

  @Column
  @Enumerated(EnumType.STRING)
  private AccessTokenType type;

  @Column(columnDefinition = "TINYINT")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private boolean isActive;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDateTime updatedAt;

  @Builder
  public AccessToken(Long userId, String token, AccessTokenType type, boolean isActive) {
    this.userId = userId;
    this.token = token;
    this.type = type;
    this.isActive = isActive;
  }
}
