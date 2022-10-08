package com.chap_9.config;

import com.chap_9.config.filter.AuthenticationLogginFilter;
import com.chap_9.config.filter.RequestValidationFilter;
import com.chap_9.config.filter.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(
//                new RequestValidationFilter(),
//                    BasicAuthenticationFilter.class)
//            .addFilterAfter(
//                new AuthenticationLogginFilter(),
//                    BasicAuthenticationFilter.class)
//                .authorizeRequests().anyRequest().authenticated();
        http.addFilterAt(staticKeyAuthenticationFilter,
                BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().permitAll();
    }
}
