package com.waste_management_backend.service;

import com.waste_management_backend.constants.ComplaintStatus;
import com.waste_management_backend.dto.ComplaintCountResponse;
import com.waste_management_backend.dto.ComplaintsDto;
import com.waste_management_backend.entity.Complaints;
import com.waste_management_backend.repository.ComplaintsRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComplaintsService {

    @Autowired
    private ComplaintsRepository complaintsRepository;

    public Page<Complaints> fetchAllComplaintsByWard(Long wardId, Pageable pageable){
        return complaintsRepository.findByWardId(wardId, pageable);
    }

    public void UpdateComplaintStatusById(Long complaintId, String status){
        complaintsRepository.updateComplaintStatusById(complaintId, status);
    }

    public Page<Complaints> fetchAllComplaintsByUser(Long userId, Pageable pageable){
        return complaintsRepository.findByUserId(userId, pageable);
    }

    public void fileComplaintByUser(ComplaintsDto userComplaint){
        Complaints complaint = new Complaints();
        complaint.setUserId(userComplaint.getUserId());
        complaint.setWardId(userComplaint.getWardId());
        complaint.setCityId(userComplaint.getCityId());
        complaint.setStateId(userComplaint.getStateId());
        complaint.setAddress(userComplaint.getAddress());
        complaint.setImageUrl(userComplaint.getImageUrl());
        complaint.setCreatedAt(userComplaint.getCreatedAt());
        complaint.setStatus(userComplaint.getStatus());
        complaint.setComplaint(userComplaint.getComplaint());
        complaint.setPinCode(userComplaint.getPinCode());
        complaintsRepository.save(complaint);
    }


    public ComplaintCountResponse getComplaintsCount(Long userId) {
        ComplaintCountResponse response = new ComplaintCountResponse();
        response.setActiveComplaints(complaintsRepository.countByUserIdAndStatus(userId, ComplaintStatus.PENDING.name()));
        response.setSolvedComplaints(complaintsRepository.countByUserIdAndStatus(userId,ComplaintStatus.SOLVED.name()));
        response.setTotalComplaints(response.getActiveComplaints()+ response.getSolvedComplaints());
        return response;
    }
}
