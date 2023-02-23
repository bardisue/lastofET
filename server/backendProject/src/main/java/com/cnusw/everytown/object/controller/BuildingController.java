package com.cnusw.everytown.object.controller;

import com.cnusw.everytown.object.service.BuildingService;
import com.cnusw.everytown.object.dto.BuildingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    @GetMapping("/buildings")
    @ResponseBody
    public ResponseEntity<List<BuildingDto>> getAllPoints(){
        return ResponseEntity.ok(buildingService.getAllBuilding());
    }

    @PostMapping("/buildings/save")
    @ResponseBody
    public ResponseEntity saveBuilding(@RequestBody BuildingDto dto){
        buildingService.createBuilding(dto);
        return ResponseEntity.ok().build();
    }
}
