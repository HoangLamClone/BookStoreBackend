package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportRepository extends JpaRepository<Import,Integer> {
    Import findImportById(int id);
    Boolean existsById(int id);
    List<Import> findImportsByCreateBy(String username);
    List<Import>  findAll(Specification<Import> spec);
}
