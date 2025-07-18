package com.waste_management_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Collate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public",name = "States")
public class States {

    @Id
    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "state_name")
    private String stateName;

}
