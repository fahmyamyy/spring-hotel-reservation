package com.amyganz.authservice.services.impl;

import com.amyganz.authservice.entities.User;
import com.amyganz.authservice.entities.UserSecurity;
import com.amyganz.authservice.exceptions.auth.UserNotExists;
import com.amyganz.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Component("UserDetails")
//@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return new UserSecurity(user.get());
        }
        throw new UserNotExists();
    }

//    public List<UserResponse> getAll() {
//
//        List<User> users = userRepository.findAll();
//        List<UserResponse> userResponseList = new ArrayList<>();
//
//        for(User user : users) {
//            userResponseList.add(modelMapper.map(user, UserResponse.class));
//        }
//
//        return userResponseList;
//    }

}
