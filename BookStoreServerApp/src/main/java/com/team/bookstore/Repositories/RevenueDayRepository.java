package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.RevenueDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RevenueDayRepository extends JpaRepository<RevenueDay, Date> {
}
