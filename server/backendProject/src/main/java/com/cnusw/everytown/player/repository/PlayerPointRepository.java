package com.cnusw.everytown.player.repository;

import com.cnusw.everytown.player.entity.PlayerPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerPointRepository extends JpaRepository<PlayerPoint, String> {
}
