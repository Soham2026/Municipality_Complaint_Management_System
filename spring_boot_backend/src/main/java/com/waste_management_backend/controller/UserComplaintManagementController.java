package com.waste_management_backend.controller;

import com.waste_management_backend.dto.ComplaintCountResponse;
import com.waste_management_backend.dto.ComplaintsDto;
import com.waste_management_backend.dto.PaginationDto;
import com.waste_management_backend.entity.Complaints;
import com.waste_management_backend.service.ComplaintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserComplaintManagementController {

    @Autowired
    private ComplaintsService complaintsService;

    @GetMapping("/get_complaints")
    public Page<Complaints> fetchAllComplaintsByUserId(@RequestParam Long userId, @ModelAttribute PaginationDto paginationDto){
        return complaintsService.fetchAllComplaintsByUser(userId,paginationDto.getPageable());
    }

    @PostMapping("/file_complaints")
    public void addComplaint(@RequestBody ComplaintsDto userComplain){
        complaintsService.fileComplaintByUser(userComplain);
    }

    @GetMapping("/getcount")
    public ComplaintCountResponse getComplaintCounts(@RequestParam Long userId){
        return complaintsService.getComplaintsCount(userId);
    }


}
