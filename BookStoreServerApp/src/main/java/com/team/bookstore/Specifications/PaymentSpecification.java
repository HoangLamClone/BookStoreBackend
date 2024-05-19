package com.team.bookstore.Specifications;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Entities.Payment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PaymentSpecification {
    public static Specification<Payment> CreatePaymentKeywordSpec(String keyword){
        return new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("id").as(String.class),likeKeyword),
                        criteriaBuilder.like(root.get("method_payment").as(String.class), likeKeyword),
                        criteriaBuilder.like(root.get("payment_status").as(String.class),likeKeyword)

                );
            }

        };
    }
}
