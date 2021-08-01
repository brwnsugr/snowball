package com.example.snowball.domain.user;

import com.example.snowball.domain.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String login;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String picture;

  @Column
  private String salt;

  @Column
  private String hashedPassword;

  @Column
  private String uuid;

  @Column
  private String lastLoggedIp;

  @Column
  private LocalDateTime lastLoggedIn;

  @Column
  private LocalDateTime lastLoggedOut;

  @Column
  private LocalDateTime lastSeenAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Builder
  public User(String name, String email, String picture, Role role, String login) {
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
    this.login = login;
  }

  public User update(String name, String picture) {
    this.name = name;
    this.picture = picture;

    return this;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }

}
