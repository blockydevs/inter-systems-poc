package com.example.poc.service;

import com.example.poc.controller.dto.CarDto;
import com.example.poc.repository.CarRepository;
import com.example.poc.repository.entity.CarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public CarEntity create(final CarDto carDto) {
        return carRepository.createCar(
                CarEntity.builder()
                        .manufacturer(carDto.manufacturer())
                        .bodyType(carDto.bodyType())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Optional<CarEntity> readCar(final Long id) {
        return carRepository.readById(id);
    }
}
