package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.StaffInformation;
import com.team.bookstore.Entities.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> GenerateUserKeywordSpec(String keyword){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyWord = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("id").as(String.class),
                        likeKeyWord),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "username")),
                                likeKeyWord)
                );
            }
        };
    }
    public static Specification<CustomerInformation> GenerateCustomerKeywordSpec(String keyword){
        return new Specification<CustomerInformation>() {
            @Override
            public Predicate toPredicate(@Nullable Root<CustomerInformation> root,@Nullable CriteriaQuery<?> query,@Nullable CriteriaBuilder criteriaBuilder) {
                if (keyword.isEmpty()) {
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "fullname")),
                                likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "email")),
                                likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "phonenumber")),
                                likeKeyword)

                );
            }
        };
    }
    public static Specification<StaffInformation> GenerateStaffKeywordSpec(String keyword){
        return new Specification<StaffInformation>() {
            @Override
            public Predicate toPredicate(@Nullable Root<StaffInformation> root,@Nullable CriteriaQuery<?> query,@Nullable CriteriaBuilder criteriaBuilder) {
                if (keyword.isEmpty() && criteriaBuilder!=null) {
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "fullname")),
                                likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "email")),
                                likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "phonenumber")),
                                likeKeyword)
                );
            }
        };
    }

}
