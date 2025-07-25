package com.waste_management_backend.repository;

import com.waste_management_backend.entity.City;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    List<City> findByStateId(Long stateId, Sort sort);
}
