package com.cnusw.everytown.object.repository;

import com.cnusw.everytown.object.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, String> {
}
