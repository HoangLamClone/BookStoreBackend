package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Book_Keyword;
import com.team.bookstore.Entities.Keyword;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword,Integer> {
    Keyword findKeywordById(int id);
    Boolean existsByid(int id);
    List<Keyword> findAll(Specification<Keyword> spec);
}
