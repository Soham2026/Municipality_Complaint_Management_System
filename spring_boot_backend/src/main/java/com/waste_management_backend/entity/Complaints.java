package com.waste_management_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(schema = "public",name = "Complaints")
public class Complaints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Long complaintId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "address")
    private String address;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "status")
    private String status;

    @Column(name = "complaint")
    private String complaint;

    @Column(name = "pin_code")
    private String pinCode;


}
