package com.greenfoxacademy.finalexamhw.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  final
  JWTTokenFilter jwtTokenFilter;

  public WebSecurityConfig(JWTTokenFilter jwtTokenFilter) {
    this.jwtTokenFilter = jwtTokenFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/register").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}