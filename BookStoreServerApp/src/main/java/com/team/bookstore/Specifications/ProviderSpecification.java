package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Provider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProviderSpecification {
    public static Specification<Provider> CreateProviderKeywordSpec(String keyword){
        return new Specification<Provider>() {
            @Override
            public Predicate toPredicate(Root<Provider> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("id").as(String.class)),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "providername")),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "address")),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "representativename")),likeKeyword)
                );
            }
        };
    }
}
