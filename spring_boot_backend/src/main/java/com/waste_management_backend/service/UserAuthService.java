package com.waste_management_backend.service;

import com.waste_management_backend.dto.PasswordResetRequest;
import com.waste_management_backend.dto.UserData;
import com.waste_management_backend.entity.User;
import com.waste_management_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public Long signUpUser(UserData userdata) {
        String encryptedPassword = passwordEncoder.encode(userdata.getPassword());
        User user = new User();
        user.setUserName(userdata.getUserName());
        user.setContactNumber(userdata.getContactNumber());
        user.setEmail(userdata.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole(userdata.getRole());

        if (userRepository.existsByContactNumber(user.getContactNumber()) == false) {
            userRepository.save(user);
            return user.getUserId();
        } else {
            return -1L;
        }
    }

    public User loginUser(String contactNumber) {
        if (userRepository.existsByContactNumber(contactNumber)) {
            User user = userRepository.findUserByContactNumber(contactNumber);
            return user;
        } else {
            return null;
        }
    }

    private Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void resetPassword(PasswordResetRequest request) {
        userRepository.updatePassword(request.getPassword(), request.getEmail());
    }


}
