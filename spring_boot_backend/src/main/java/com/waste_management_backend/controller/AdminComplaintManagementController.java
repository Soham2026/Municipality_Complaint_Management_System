package com.waste_management_backend.controller;


import com.waste_management_backend.dto.ComplaintStatusUpdateDto;
import com.waste_management_backend.dto.PaginationDto;
import com.waste_management_backend.entity.Complaints;
import com.waste_management_backend.service.ComplaintsService;
import com.waste_management_backend.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminComplaintManagementController {

    @Autowired
    private ComplaintsService complaintsService;

    @Autowired
    private RolesService rolesService;

    @GetMapping("complaints/{wardId}")
    public Page<Complaints> fetchAllComplaintsByWard(@PathVariable Long wardId, @RequestParam int pageNumber, @RequestParam int pageSize){
        PaginationDto paginationDto = new PaginationDto(pageNumber,pageSize);
        return complaintsService.fetchAllComplaintsByWard(wardId,paginationDto.getPageable());
    }

    @PutMapping("/complaint-status-update")
    public void updateComplaintStatus(@RequestBody ComplaintStatusUpdateDto statusUpdateDto){
        complaintsService.UpdateComplaintStatusById(statusUpdateDto.getId(),statusUpdateDto.getStatus());
    }

    @GetMapping("get_ward/{adminId}")
    public Long fetchWardIdByAdminId(@PathVariable Long adminId){
        return rolesService.getWardId(adminId);
    }




}
