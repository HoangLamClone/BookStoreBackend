package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Shift;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ShiftSpecification {
    public static Specification<Shift> CreateShiftKeywordSpec(String keyword){
        return new Specification<Shift>() {
            @Override
            public Predicate toPredicate(Root<Shift> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("id").as(String.class)),likeKeyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(
                            "start_time").as(String.class)),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "end_time").as(String.class)),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "description")),likeKeyword)
                );
            }
        };
    }
}
