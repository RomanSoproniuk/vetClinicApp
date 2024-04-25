package com.coolvetclinicpumb.vetclinicapp.mapper;

import com.coolvetclinicpumb.vetclinicapp.config.MapperConfig;
import com.coolvetclinicpumb.vetclinicapp.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    Category.TypeCategory toCategory(String string);
}
