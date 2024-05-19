package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.ComposeKey.OrderDetailKey;
import com.team.bookstore.Entities.Order_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_DetailRepository extends JpaRepository<Order_Detail, OrderDetailKey> {
}
