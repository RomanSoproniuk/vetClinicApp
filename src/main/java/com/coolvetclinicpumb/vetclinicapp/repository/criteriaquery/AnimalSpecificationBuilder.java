package com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery;

import com.coolvetclinicpumb.vetclinicapp.dto.AnimalSearchParameters;
import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalSpecificationBuilder implements SpecificationBuilder<Animal> {
    private static final String KEY_FOR_TYPE_SPECIFICATION_PROVIDER = "type";
    private static final String KEY_FOR_SEX_SPECIFICATION_PROVIDER = "sex";
    private static final String KEY_FOR_CATEGORY_SPECIFICATION_PROVIDER = "category";
    private static final byte MINIMAL_CAPACITY = 0;
    private final SpecificationProviderManager<Animal> animalSpecificationProviderManager;

    @Override
    public Specification<Animal> build(AnimalSearchParameters animalSearchParameters) {
        Specification<Animal> specification = Specification.where(null);
        if (animalSearchParameters.types() != null
                && animalSearchParameters.types().length > MINIMAL_CAPACITY) {
            specification = specification.and(animalSpecificationProviderManager
                    .getSpecificationProvider(KEY_FOR_TYPE_SPECIFICATION_PROVIDER)
                    .getSpecification(animalSearchParameters.types()));
        }
        if (animalSearchParameters.sex() != null
                && animalSearchParameters.sex().length > MINIMAL_CAPACITY) {
            specification = specification.and(animalSpecificationProviderManager
                    .getSpecificationProvider(KEY_FOR_SEX_SPECIFICATION_PROVIDER)
                    .getSpecification(animalSearchParameters.sex()));
        }
        if (animalSearchParameters.categories() != null
                && animalSearchParameters.categories().length > MINIMAL_CAPACITY) {
            specification = specification.and(animalSpecificationProviderManager
                    .getSpecificationProvider(KEY_FOR_CATEGORY_SPECIFICATION_PROVIDER)
                    .getSpecification(animalSearchParameters.categories()));
        }
        return specification;
    }
}
