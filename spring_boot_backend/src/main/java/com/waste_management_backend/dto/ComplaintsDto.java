package com.waste_management_backend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintsDto {
    private Long userId;
    private Long wardId;
    private Long cityId;
    private Long stateId;
    private String address;
    private String imageUrl;
    private OffsetDateTime createdAt;
    private String status;
    private String complaint;
    private String pinCode;
}
