package com.cnusw.everytown.object.repository;

import com.cnusw.everytown.object.entity.Object;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends JpaRepository<Object, String> {
}
