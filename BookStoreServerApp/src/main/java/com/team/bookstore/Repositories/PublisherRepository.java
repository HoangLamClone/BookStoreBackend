package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Publisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Integer> {
    Publisher findPublisherById(int id);
    Boolean existsById(int id);
    List<Publisher> findAll(Specification<Publisher> spec);
}
