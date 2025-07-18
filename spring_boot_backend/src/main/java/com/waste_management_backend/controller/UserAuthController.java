package com.waste_management_backend.controller;

import com.waste_management_backend.dto.LoginRequest;
import com.waste_management_backend.dto.AuthResponse;
import com.waste_management_backend.dto.PasswordResetRequest;
import com.waste_management_backend.dto.UserData;
import com.waste_management_backend.entity.User;
import com.waste_management_backend.service.UserAuthService;
import com.waste_management_backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/user_signup")
    public ResponseEntity<AuthResponse> signUpUser(@RequestBody UserData userData) {
        Long id = userAuthService.signUpUser(userData);
        if (id != -1) {
            String token = jwtUtil.generateToken(userData.getContactNumber(), userData.getRole());
            return ResponseEntity.ok(new AuthResponse(token, id, userData.getRole(), userData.getUserName(), userData.getEmail(), userData.getContactNumber()));
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getContactNumber(), user.getPassword()));
            User userFromDb = userAuthService.loginUser(user.getContactNumber());
            String token = jwtUtil.generateToken(userFromDb.getContactNumber(), userFromDb.getRole());
            return new ResponseEntity<>(new AuthResponse(token, userFromDb.getUserId(), userFromDb.getRole(), userFromDb.getUserName(), userFromDb.getEmail(), userFromDb.getContactNumber()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
