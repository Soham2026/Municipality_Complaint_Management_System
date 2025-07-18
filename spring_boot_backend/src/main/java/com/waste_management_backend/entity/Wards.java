package com.waste_management_backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public",name = "Wards")
public class Wards {

    @Id
    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "city_id")
    private Long cityId;

}
