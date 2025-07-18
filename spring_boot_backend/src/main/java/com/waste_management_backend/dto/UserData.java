package com.waste_management_backend.dto;
import lombok.Data;

@Data
public class UserData {
    private String userName;
    private String contactNumber;
    private String email;
    private String password;
    private String role;
}
