package com.chap_8.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
//                .mvcMatchers("/chap8").hasAnyAuthority("READ","WRITE")
                .antMatchers("/chap8").hasAnyAuthority("READ","WRITE")
                .anyRequest().authenticated();
    }
}
