package com.cnusw.everytown.marker.repository;

import com.cnusw.everytown.marker.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Integer> {

    @Override
    List<Marker> findAll();


    @Query(value = "SELECT 1 FROM marker WHERE x=:x AND y=:y LIMIT 1",
            nativeQuery = true)
    Optional<Integer> existsByPoint(@Param("x") int x, @Param("y") int y);


    @Query(value = "SELECT * FROM marker WHERE x=:x AND y=:y LIMIT 1",
            nativeQuery = true)
    Marker findByPoint(@Param("x") int x, @Param("y") int y);

}