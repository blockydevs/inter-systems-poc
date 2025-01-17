package com.example.poc.controller;

import com.example.poc.controller.dto.CarDto;
import com.example.poc.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;

    @PostMapping
    public CarDto create(@RequestBody final CarDto carDto) {
        final var car = carService.create(carDto);
        return new CarDto(car.getId(), car.getManufacturer(), car.getBodyType());
    }

    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable final Long id) {
        final var car = carService.readCar(id);
        return car
                .map(c -> new CarDto(c.getId(), c.getManufacturer(), c.getBodyType()))
                .orElse(null);
    }
}
