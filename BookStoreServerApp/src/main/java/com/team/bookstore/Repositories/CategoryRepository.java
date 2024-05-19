package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryById(int id);
    Boolean existsById(int id);
    List<Category> findAll(Specification<Category> spec);
}
