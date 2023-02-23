package com.cnusw.everytown.object.service;

import com.cnusw.everytown.object.dto.BuildingDto;
import com.cnusw.everytown.object.entity.Building;
import com.cnusw.everytown.object.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public List<BuildingDto> getAllBuilding() {
        List<Building> list = buildingRepository.findAll();

        List<BuildingDto> dtos = list.stream().map(BuildingDto::toDto).collect(Collectors.toList());
        return dtos;
    }

    public void createBuilding(BuildingDto dto) {
        Building building = Building.toEntity(dto);
        buildingRepository.save(building);
    }
}
