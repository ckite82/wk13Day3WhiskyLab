package com.codeclan.example.WhiskyTracker.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.codeclan.example.WhiskyTracker.models.Distillery;
import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import java.util.ArrayList;
@RestController
public class DistilleryController {

    @Autowired
    DistilleryRepository distilleryRepository;
    @Autowired
    WhiskyRepository whiskyRepository;

    @GetMapping(value="/distilleries")
    public ResponseEntity<List<Distillery>> getDistilleriesByRegion(
            @RequestParam(name="region", required = false) String region,
            @RequestParam(name="age", required = false) Integer age){
        if(region != null){
            return new ResponseEntity<>(distilleryRepository.findByRegion(region), HttpStatus.OK);
        }
        if(age != null){
            List<Whisky> whiskys = whiskyRepository.findWhiskeyByAgeGreaterThan(age);
            ArrayList<Distillery> distilleries = new ArrayList<>();
            for(int i = 0; i < whiskys.size(); i++) { //0 print everything -- 1 print everything -- 2 print everything etc
                Distillery distillery = whiskys.get(i).getDistillery();
                distilleries.add(distillery);
            }

            return new ResponseEntity<>(distilleries, HttpStatus.OK);
        }
        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }

}
