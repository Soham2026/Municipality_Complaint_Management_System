package com.waste_management_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long id;
    private String role;
    private String userName;
    private String email;
    private String contactNumber;
}
