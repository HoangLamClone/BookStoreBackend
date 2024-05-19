package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Feedback;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    Feedback findFeedbackById(int id);
    Boolean existsById(int id);
    List<Feedback> findAll(Specification<Feedback> spec);
}
