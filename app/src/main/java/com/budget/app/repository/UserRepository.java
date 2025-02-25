package com.budget.app.repository;

import com.budget.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    // Follows JPA Query Methods
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    User findByEmailAddress(String email);
}
