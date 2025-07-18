package com.waste_management_backend.controller;

import com.waste_management_backend.service.CityService;
import com.waste_management_backend.service.StatesService;
import com.waste_management_backend.service.WardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private StatesService statesService;

    @Autowired
    private CityService cityService;

    @Autowired
    private WardsService wardsService;

    @GetMapping("/get_states")
    public Map<String,String> getStates(){
        return statesService.getStates();
    }

    @GetMapping("/get_cities")
    public Map<String,String> getCities(@RequestParam Long stateId){
        return cityService.findCitiesByState(stateId);
    }

    @GetMapping("/get_wards")
    public Map<String,String> getWards(@RequestParam Long cityId){
        return wardsService.findWardsByCity(cityId);
    }

}
