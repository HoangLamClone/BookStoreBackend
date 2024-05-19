package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.ComposeKey.StaffShiftKey;
import com.team.bookstore.Entities.Staff_Shift;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface Staff_ShiftRepository extends JpaRepository<Staff_Shift, StaffShiftKey>{
    Staff_Shift findStaff_ShiftById(StaffShiftKey id);
    boolean existsById(StaffShiftKey id);
    Set<Staff_Shift>  findAll(Specification<Staff_Shift> spec);
}
