package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.ComposeKey.CustomerBookKey;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.Customer_Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Customer_BookRepository extends JpaRepository<Customer_Book, CustomerBookKey> {
    boolean existsCustomer_BookById(CustomerBookKey id);
    List<Customer_Book> findAll(Specification<Customer_Book> spec);
    Customer_Book findCustomer_BookById(CustomerBookKey id);
}
