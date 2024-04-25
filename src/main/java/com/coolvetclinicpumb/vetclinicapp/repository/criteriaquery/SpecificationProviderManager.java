package com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String key);
}
