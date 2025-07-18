package com.waste_management_backend.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public <T>T getFromRedis(String key, TypeReference<T> typeRef){
        try{
            String jsonData = redisTemplate.opsForValue().get(key).toString();
            return objectMapper.readValue(jsonData, typeRef);
        }catch (Exception e){
            log.error("got an exeption");
            return null;
        }
    }

    public<T> void updateRedis(String key, T value){
        try{
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,jsonValue);
        }catch (Exception e){
            log.error("Couldnt update for key:"+ key +" , Exception :"+e);
        }

    }

}
