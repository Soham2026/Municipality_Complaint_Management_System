package com.waste_management_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.waste_management_backend.entity.States;
import com.waste_management_backend.repository.StatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class StatesService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private StatesRepository statesRepository;

    public Map<String,String> getStates(){
        Map<String,String> states = redisService.getFromRedis("STATES", new TypeReference<>() {});

        if( states != null){
            log.error("Fetched from redis");
            return states;
        }else{
            List<States> statesList = new ArrayList<>();
            statesList = statesRepository.findAll(Sort.by("stateName"));
            states = getStateMapFromList(statesList);
            log.error("Fetched from db");
            redisService.updateRedis("STATES",states);
            return states;
        }
    }

    private Map<String,String> getStateMapFromList(List<States> statesList){
        Map<String,String> map =new LinkedHashMap<>();
        for(States stateInfo: statesList){
            map.put(stateInfo.getStateName(),stateInfo.getStateId().toString());
        }
        return map;
    }


}
