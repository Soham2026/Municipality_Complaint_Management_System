package com.waste_management_backend.repository;


import com.waste_management_backend.entity.Wards;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardsRepository extends JpaRepository<Wards,Long> {
    List<Wards> findByCityId(Long cityId, Sort sort);
}
