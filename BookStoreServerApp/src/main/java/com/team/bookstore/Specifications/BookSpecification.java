package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.CustomerInformation;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> GenerateBookKeywordSpec(@NotNull String keyword){
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (keyword.isEmpty()) {
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("id").as(String.class)
                        , likeKeyword.toLowerCase()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "title")),
                                likeKeyword.toLowerCase()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "description")),
                                likeKeyword.toLowerCase())
                );
            }
        };
    }
}
