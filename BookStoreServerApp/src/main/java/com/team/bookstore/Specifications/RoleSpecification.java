package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Role;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {
    public static Specification<Role> GenerateRoleKeywordSpec(String keyword){
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "rolename")),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "description")),likeKeyword)
                );
            }
        };
    }
}
