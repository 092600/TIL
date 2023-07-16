package com.chap_6.authentication.userDetailsService;

import com.chap_6.authentication.userDetails.Chap6UserDetails;
import com.chap_6.domain.user.User;
import com.chap_6.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Chap6UserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Chap6UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User u = userRepository.findUserByUsername(username);
            Chap6UserDetails chap6UserDetails = new Chap6UserDetails(u);

            return chap6UserDetails;

        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
