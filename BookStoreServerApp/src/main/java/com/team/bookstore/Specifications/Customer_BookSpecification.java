package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Customer_Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
@Log4j2
public class Customer_BookSpecification {
    public static Specification<Customer_Book> CreateCustomerBookByCustomerIDSpec(Integer id){
        return new Specification<Customer_Book>() {
            @Override
            public Predicate toPredicate(Root<Customer_Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(id == null){
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.equal(root.get("id").get("customer_id"), id);
            }
        };
    }
}
