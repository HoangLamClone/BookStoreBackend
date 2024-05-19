package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.GalleryManage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GallerySpecification {
    public static Specification<GalleryManage> CreateGalleryKeywordSpec(String keyword){
        return new Specification<GalleryManage>() {
            @Override
            public Predicate toPredicate(Root<GalleryManage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("id").as(String.class)),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "description")),likeKeyword)
                );
            }
        };
    }
}
