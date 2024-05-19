package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Shift;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,Integer> {
    Shift findShiftById(int id);
    Boolean existsById(int id);
    List<Shift> findAll(Specification<Shift> spec);
}

