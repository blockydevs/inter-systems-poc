package com.example.poc.repository;

import com.example.poc.repository.entity.CarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarRepository {

    private final CarJpaRepository carJpaRepository;

    public CarEntity createCar(CarEntity carEntity) {
        return carJpaRepository.save(carEntity);
    }

    public Optional<CarEntity> readById(Long id) {
        return carJpaRepository.findById(id);
    }
}
