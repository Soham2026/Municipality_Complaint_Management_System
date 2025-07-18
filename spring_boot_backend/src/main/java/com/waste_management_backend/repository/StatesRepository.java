package com.waste_management_backend.repository;

import com.waste_management_backend.entity.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesRepository extends JpaRepository<States,Long> {

}
