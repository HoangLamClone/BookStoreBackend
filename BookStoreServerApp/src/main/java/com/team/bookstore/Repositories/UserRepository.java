package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    User findUserById(int id);
    User findUsersByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsById(int id);
    List<User> findAll(Specification<User> spec);
}
