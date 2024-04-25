package com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.animal;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TypeSpecificationProvider implements SpecificationProvider<Animal> {
    public Specification<Animal> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("type")
                .in(Arrays.stream(params).toArray());
    }

    @Override
    public String getKey() {
        return "type";
    }
}
