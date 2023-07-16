package com.chap_12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.oauth2Login(c -> {
//            c.clientRegistrationRepository(clientRegistrationRepository());
//        });
//
//        http.authorizeRequests().anyRequest().authenticated();
//    }
//
//    public ClientRegistrationRepository clientRegistrationRepository(){
//        var c = clientRegistration();
//        return new InMemoryClientRegistrationRepository(c);
//    }
//
//    private ClientRegistration clientRegistration(){
//        return CommonOAuth2Provider.GITHUB.getBuilder("github")
//                .clientId("5a77ea6eed520d9d43c3")
//                .clientSecret("8a2cc56faa941b902a97d2c2911f9bc2e708fafc")
//                .build();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
