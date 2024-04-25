package com.coolvetclinicpumb.vetclinicapp.dto;

public record AnimalResponseDto(Long id,
                                String name,
                                String type,
                                String sex,
                                Integer weight,
                                Integer cost,
                                CategoryResponseDto category) {
}
