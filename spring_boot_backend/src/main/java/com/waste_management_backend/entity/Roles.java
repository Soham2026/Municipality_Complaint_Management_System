package com.waste_management_backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "public",name = "Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "admin_id")
    private Long adminId;
}
