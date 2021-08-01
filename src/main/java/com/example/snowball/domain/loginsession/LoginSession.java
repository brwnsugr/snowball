package com.example.snowball.domain.loginsession;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "login_session")
public class LoginSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(length = 11)
  private Long id;

  @Column(updatable = false)
  private Long userId;

  @Column
  private String sessionUuid;

  @Column
  private String meta;

  @Column
  private String remoteAddr;

  @Column(columnDefinition = "TINYINT")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private Boolean isActive;

  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDateTime loginAt;

  @Column
  private LocalDateTime logoutAt;

  @CreationTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDateTime updatedAt;


}
