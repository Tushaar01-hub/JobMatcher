package com.jobMatcher.project.security;

import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    public CustomerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("LOGIN USERNAME = " + username);
        User user=userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Invalid Email"));
        System.out.println("USER FOUND = " + user.getEmail());
        return new CustomerUserDetails(user);
    }
}
