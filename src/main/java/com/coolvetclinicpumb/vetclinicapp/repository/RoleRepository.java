package com.coolvetclinicpumb.vetclinicapp.repository;

import com.coolvetclinicpumb.vetclinicapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
