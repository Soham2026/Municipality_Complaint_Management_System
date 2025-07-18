package com.waste_management_backend.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.waste_management_backend.entity.City;
import com.waste_management_backend.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CityService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private CityRepository cityRepository;

    public Map<String,String> findCitiesByState(Long stateId){
        Map<String,String> cities = redisService.getFromRedis(stateId.toString(), new TypeReference<>() {});

        if(cities != null){
            log.error("Fetched cities from redis");
            return cities;
        }else{
            List<City> cityList = cityRepository.findByStateId(stateId, Sort.by(Sort.Direction.ASC,"cityName"));
            cities = getCityMapFromList(cityList);
            log.error("Fetched cities from db");
            redisService.updateRedis(stateId.toString(),cities);
            return cities;
        }
    }

    private Map<String,String> getCityMapFromList(List<City> cityList){
        Map<String,String> map = new LinkedHashMap<>();
        for(City city : cityList){
            map.put(city.getCityName(),city.getCityId().toString());
        }
        return map;
    }

}
