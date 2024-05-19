package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,Integer> {
    boolean existsById(String id);
}
