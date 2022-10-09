package com.chap_10.config;

import com.chap_10.config.filter.CsrfTokenLogger;
import com.chap_10.domain.CustomCsrfTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CsrfTokenRepository customTokenRepository(){
        return new CustomCsrfTokenRepository();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf(c -> {
                    c.csrfTokenRepository(customTokenRepository());
                });
        http.formLogin();
        http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class).
                authorizeRequests().anyRequest().authenticated();
    }
}
