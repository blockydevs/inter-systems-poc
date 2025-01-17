package com.example.poc.repository;

import com.example.poc.repository.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarJpaRepository extends JpaRepository<CarEntity, Long> {
}
