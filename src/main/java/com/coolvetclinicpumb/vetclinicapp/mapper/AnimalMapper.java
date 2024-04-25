package com.coolvetclinicpumb.vetclinicapp.mapper;

import com.coolvetclinicpumb.vetclinicapp.config.MapperConfig;
import com.coolvetclinicpumb.vetclinicapp.dto.AnimalResponseDto;
import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AnimalMapper {
    AnimalResponseDto toDto(Animal animal);
}
