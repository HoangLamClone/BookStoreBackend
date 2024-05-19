package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Staff_Shift;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class Staff_ShiftSpecification {
    public static Specification<Staff_Shift> CreateStaff_ShiftKeywordSpec(String keyword){
        return new Specification<Staff_Shift>() {
            @Override
            public Predicate toPredicate(Root<Staff_Shift> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()) {
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase()+ "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("hasWorkThisShift").as(String.class),
                                likeKeyword)
                );
            }
        };
    }
    public static Specification<Staff_Shift> CreateStaff_ShiftStaffIdSpec(Integer id){
        return new Specification<Staff_Shift>() {
            @Override
            public Predicate toPredicate(Root<Staff_Shift> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(id == null && criteriaBuilder!=null){
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.equal(root.get("id").get("staff_id"),id);
            }
        };
    }
}

