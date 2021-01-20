package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    @Autowired
    DistilleryRepository distilleryRepository;

    @GetMapping(value = "/whiskys")
    public ResponseEntity<List<Whisky>> findWhiskyAndFilter(@RequestParam(name="age", required = false) Integer age,
                                                            @RequestParam(name="distillery", required = false) String distillery) {
        if(distillery != null && age != null) {
            List<Whisky> whiskys = getWhiskyWithDistilleryAndAge(age, distillery);
            return new ResponseEntity<>(whiskys, HttpStatus.OK);
        }
        if(distillery != null) {
            return new ResponseEntity<>(getWhiskyWithDestileryRegion(distillery), HttpStatus.OK);
        }
        if (age != null) {
            return new ResponseEntity<>(whiskyRepository.findWhiskyByAge(age), HttpStatus.OK);
        }
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }


    private List<Whisky> getWhiskyWithDistilleryAndAge(Integer age, String distillery) {
        List<Distillery> distilleryFromDatabase = distilleryRepository.findByRegion(distillery);        // Finding distillery from database
        return whiskyRepository.findWhiskyByDistilleryAndAge(distilleryFromDatabase.get(0), age);   // Find the whisky with the distiller and age
    }

    private List<Whisky> getWhiskyWithDestileryRegion(String distillery) {
        System.out.println(distilleryRepository.findByRegion("Lowland"));
        List<Distillery> distilleryFromDatabase = distilleryRepository.findByRegion("Lowland");        // Finding distillery from the database
            return whiskyRepository.findWhiskyByDistilleryRegion(distilleryFromDatabase.get(0).getRegion()); // Find the whiskys with the distillery region
    }
}