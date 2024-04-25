package com.coolvetclinicpumb.vetclinicapp.repository;

import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnimalRepository extends JpaRepository<Animal, Short>,
        JpaSpecificationExecutor<Animal> {
}
