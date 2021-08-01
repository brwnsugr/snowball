package com.example.snowball.config.auth;

import com.example.snowball.config.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  private static final String[] PUBLIC_URI = {
   "/**","api/v1/**", "swagger-ui.html", "/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile", "/api/v1/hello"

  };
//
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/api/v1/**", "swagger-ui.html", "/**");
//    web.ignoring().antMatchers("/swagger-ui.html");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // 개발 편의성을 위해 CSRF 프로텍션을 비활성화
        .csrf()
        .disable()
        // HTTP 기본 인증 비활성화
        .httpBasic()
        .disable()
        // 폼 기반 인증 비활성화
        .formLogin()
        .disable()
        // stateless한 세션 정책 설정
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // 리소스 별 허용 범위 설정
        .authorizeRequests()
//        .antMatchers(HttpMethod.GET,"/api/v1/hello", "/api/v1/hello/**")
//        .permitAll()
        .anyRequest()
        .permitAll();
//        .authenticated();


    http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
  }

}
