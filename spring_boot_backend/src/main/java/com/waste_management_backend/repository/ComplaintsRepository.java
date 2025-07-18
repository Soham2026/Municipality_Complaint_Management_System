package com.waste_management_backend.repository;

import com.waste_management_backend.entity.Complaints;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintsRepository extends JpaRepository<Complaints,Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE \"Complaints\" SET status = :status WHERE complaint_id = :complaintId",nativeQuery = true)
    void updateComplaintStatusById(@Param("complaintId") Long complaintId, @Param("status") String status);

    Page<Complaints> findByWardId(Long wardId, Pageable pageable);

    Page<Complaints> findByUserId(Long userId, Pageable pageable);

    int countByUserIdAndStatus(Long userId, String status);


}
