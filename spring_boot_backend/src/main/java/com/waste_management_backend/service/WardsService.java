package com.waste_management_backend.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.waste_management_backend.entity.Wards;
import com.waste_management_backend.repository.WardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WardsService {

    @Autowired
    private WardsRepository wardsRepository;

    @Autowired
    private RedisService redisService;

    public Map<String,String> findWardsByCity(Long cityId){
        Map<String,String> wards = redisService.getFromRedis("CITY"+cityId.toString(),new TypeReference<>(){});
        if(wards == null){
            List<Wards> wardsList = wardsRepository.findByCityId(cityId, Sort.by(Sort.Direction.ASC,"wardId"));
            wards = getWardMapFromList(wardsList);
            redisService.updateRedis("CITY"+cityId.toString(),wards);
        }
        return wards;
    }


    private Map<String,String> getWardMapFromList( List<Wards> wardsList){
        Map<String,String> map = new LinkedHashMap<>();
        for(Wards ward :wardsList){
            map.put(ward.getWardName(),ward.getWardId().toString());
        }
        return map;
    }

}
