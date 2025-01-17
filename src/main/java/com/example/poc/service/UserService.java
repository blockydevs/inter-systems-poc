package com.example.poc.service;

import com.example.poc.controller.dto.CarDto;
import com.example.poc.controller.dto.UserDto;
import com.example.poc.repository.UserRepository;
import com.example.poc.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CarService carService;

    @Transactional
    public UserEntity create(final UserDto userDto) {
        return userRepository.createUser(
                UserEntity.builder()
                        .username(userDto.username())
                        .description(userDto.description())
                        .cars(userDto.cars().stream()
                                .map(CarDto::id)
                                .map(carService::readCar)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .toList()
                        )
                        .build()
        );
    }

    @Transactional
    public List<UserEntity> createBatch(final List<UserDto> userDtoList) {
        return userRepository.createUsers(
                userDtoList.stream()
                        .map(userDto -> UserEntity.builder()
                                .username(userDto.username())
                                .description(userDto.description())
                                .cars(userDto.cars().stream()
                                        .map(CarDto::id)
                                        .map(carService::readCar)
                                        .filter(Optional::isPresent)
                                        .map(Optional::get)
                                        .toList()
                                )
                                .build())
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public List<UserEntity> readAll() {
        return userRepository.readAll();
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> readUser(final Long id) {
        return userRepository.readById(id);
    }
}
