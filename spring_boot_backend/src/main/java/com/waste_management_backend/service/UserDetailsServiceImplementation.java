package com.waste_management_backend.service;

import com.waste_management_backend.entity.User;
import com.waste_management_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String contactNumber) throws UsernameNotFoundException {

        User user = userRepository.findUserByContactNumber(contactNumber);

        if (user != null) {
           return  org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }

        throw new UsernameNotFoundException("Invalid Credentials!!");
    }
}
