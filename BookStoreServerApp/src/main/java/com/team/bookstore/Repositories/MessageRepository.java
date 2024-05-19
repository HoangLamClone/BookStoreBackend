package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Message;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    Message findMessageById(int id);
    boolean existsById(int id);
    List<Message> findAll(Specification<Message> spec);
}
