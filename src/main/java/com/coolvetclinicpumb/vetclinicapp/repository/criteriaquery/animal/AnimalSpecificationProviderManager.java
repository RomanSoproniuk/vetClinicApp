package com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.animal;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.SpecificationProvider;
import com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.SpecificationProviderManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalSpecificationProviderManager implements SpecificationProviderManager<Animal> {
    private final List<SpecificationProvider<Animal>> animalSpecificationProviders;

    @Override
    public SpecificationProvider<Animal> getSpecificationProvider(String key) {
        return animalSpecificationProviders.stream()
                .filter(animal -> animal.getKey().equals(key))
                .findFirst().orElseThrow(() ->
                        new EntityNotFoundException("Can't find correct specification"
                                + " provider for key: " + key));
    }
}
