package com.cnusw.everytown.marker.repository;

import com.cnusw.everytown.marker.entity.LossMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LossMarkerRepository extends JpaRepository<LossMarker, Integer> {


    @Query(value = "SELECT * FROM loss_marker ORDER BY created_datetime DESC",
            nativeQuery = true)
    @Override
    List<LossMarker> findAll();
}