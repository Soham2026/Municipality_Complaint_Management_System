package com.waste_management_backend.repository;

import com.waste_management_backend.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Roles findByAdminId(Long adminId);
}
