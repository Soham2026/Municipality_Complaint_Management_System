package com.waste_management_backend.repository;

import com.waste_management_backend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByContactNumber(String contactNumber);

    User findUserByContactNumber(String contactNumber);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE \"Users\" SET password = :hashedPassword WHERE email = :email",nativeQuery = true)
    void updatePassword(@Param("hashedPassword") String hashedPassword, @Param("email") String email);

}
