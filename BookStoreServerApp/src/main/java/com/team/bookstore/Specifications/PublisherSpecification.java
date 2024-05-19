package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Publisher;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PublisherSpecification {
    public static Specification<Publisher> CreatePublisherKeywordSpec(String keyword){
        return new Specification<Publisher>() {
            @Override
            public Predicate toPredicate(Root<Publisher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("id").as(String.class)),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "publisher_name")),likeKeyword)
                );
            }
        };
    }
}
