package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Author;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findAuthorById(int id);
    Boolean existsAuthorById(int id);
    List<Author> findAll(Specification<Author> spec);
}
