//package com.amyganz.authservice.services.impl;
//
//import com.amyganz.authservice.entities.Receptionist;
//import com.amyganz.authservice.entities.ReceptionistSecurity;
//import com.amyganz.authservice.entities.User;
//import com.amyganz.authservice.entities.UserSecurity;
//import com.amyganz.authservice.repositories.ReceptionistRepository;
//import com.amyganz.authservice.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
////@Primary
//@Component("RecepDetails")
//@RequiredArgsConstructor
//@Service
//public class JpaReceptionistDetailService implements UserDetailsService {
//    private final ReceptionistRepository receptionistRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Receptionist> receptionist = receptionistRepository.findByUsername(username);
//        if (receptionist.isPresent()) {
//            return new ReceptionistSecurity(receptionist.get());
//        }
//        throw new UsernameNotFoundException("username not found");
//    }
//
////    public List<UserResponse> getAll() {
////
////        List<User> users = userRepository.findAll();
////        List<UserResponse> userResponseList = new ArrayList<>();
////
////        for(User user : users) {
////            userResponseList.add(modelMapper.map(user, UserResponse.class));
////        }
////
////        return userResponseList;
////    }
//
//}
