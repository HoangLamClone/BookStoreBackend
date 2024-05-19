package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Payment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findPaymentById(int id);
    List<Payment> findPaymentsByCustomerId(int customer_id);
    boolean existsById(int id);
    List<Payment> findAll(Specification<Payment> spec);
}
