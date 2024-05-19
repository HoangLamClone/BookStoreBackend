package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Language;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language,Integer> {
    Language findLanguageById(int id);
    Boolean existsById(int id);
    List<Language> findAll(Specification<Language> spec);
}
