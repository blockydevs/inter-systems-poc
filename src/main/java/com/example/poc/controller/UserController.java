package com.example.poc.controller;

import com.example.poc.controller.dto.CarDto;
import com.example.poc.controller.dto.UserDto;
import com.example.poc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@RequestBody final UserDto userDto) {
        final var user = userService.create(userDto);
        return new UserDto(user.getId(), user.getUsername(), user.getDescription(),
                user.getCars().stream()
                        .map(car -> new CarDto(car.getId(), car.getManufacturer(), car.getBodyType())).toList()
        );
    }

    @PostMapping("/batch")
    public List<UserDto> createBatch(@RequestBody final List<UserDto> userDtos) {
        final var users = userService.createBatch(userDtos);
        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getDescription(),
                        user.getCars().stream()
                                .map(car -> new CarDto(car.getId(), car.getManufacturer(), car.getBodyType())).toList())
                )
                .toList();
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.readAll().stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getDescription(),
                        user.getCars().stream()
                                .map(car -> new CarDto(car.getId(), car.getManufacturer(), car.getBodyType())).toList())
                )
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable final Long id) {
        final var user = userService.readUser(id);
        return user
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getDescription(),
                        u.getCars().stream()
                                .map(car -> new CarDto(car.getId(), car.getManufacturer(), car.getBodyType())).toList())
                )
                .orElse(null);
    }
}
