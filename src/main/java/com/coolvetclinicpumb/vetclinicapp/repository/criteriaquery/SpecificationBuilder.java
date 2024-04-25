package com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(AnimalSearchParameters animalSearchParameters);
}
