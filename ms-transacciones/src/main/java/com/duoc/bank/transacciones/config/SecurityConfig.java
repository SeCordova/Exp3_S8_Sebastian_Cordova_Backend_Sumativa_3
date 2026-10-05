package com.duoc.bank.transacciones.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
 @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http.csrf(csrf->csrf.disable())
      .authorizeHttpRequests(auth->auth.requestMatchers("/actuator/health","/actuator/info").permitAll().anyRequest().authenticated())
      .oauth2ResourceServer(oauth->oauth.jwt(jwt->{}));
  return http.build();
 }
}
