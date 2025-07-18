package com.waste_management_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComplaintStatusUpdateDto {
    private Long id;
    private String status;
}
