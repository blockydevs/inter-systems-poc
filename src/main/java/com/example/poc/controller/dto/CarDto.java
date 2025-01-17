package com.example.poc.controller.dto;

import com.example.poc.repository.entity.BodyType;

public record CarDto(Long id, String manufacturer, BodyType bodyType) {
}
