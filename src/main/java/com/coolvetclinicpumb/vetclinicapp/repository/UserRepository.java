package com.coolvetclinicpumb.vetclinicapp.repository;

import com.coolvetclinicpumb.vetclinicapp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u LEFT JOIN FETCH u.roles WHERE u.email=:email")
    Optional<User> findByEmail(String email);

    @Override
    @Query("SELECT u FROM users u LEFT JOIN FETCH u.roles WHERE u.id=:id")
    Optional<User> findById(Long id);
}
