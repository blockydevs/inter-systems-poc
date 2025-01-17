package com.example.poc.controller.dto;

import java.util.List;

public record UserDto(Long id, String username, String description, List<CarDto> cars) {
}
