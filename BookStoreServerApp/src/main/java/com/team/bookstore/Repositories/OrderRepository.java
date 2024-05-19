package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Book_Author;
import com.team.bookstore.Entities.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findOrderById(int it);
    List<Order> findOrdersByCustomerId(int customer_id);
    Boolean existsById(int id);
    List<Order> findAll(Specification<Order> spec);
}
