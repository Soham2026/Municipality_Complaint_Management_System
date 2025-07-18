package com.waste_management_backend.service;

import com.waste_management_backend.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public Long getWardId(Long adminId){
        return rolesRepository.findByAdminId(adminId).getWardId();
    }
}
