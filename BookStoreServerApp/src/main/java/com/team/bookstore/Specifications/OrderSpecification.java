package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
@Log4j2
public class OrderSpecification {
    public static Specification<Order> CreateOrderKeywordSpec(String keyword){
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "id").as(String.class)),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "order_note")),likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "address")),likeKeyword)
                );
            }
        };
    }
    public static Specification<Order> CreateOrderDateSpec(String date){
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(date.length()!=10 && criteriaBuilder!=null){
                    return criteriaBuilder.conjunction();
                }
                SimpleDateFormat sdf       = new SimpleDateFormat("yyyy-MM-dd");
                Timestamp        startTime = null;
                try {
                    startTime = new Timestamp(sdf.parse(date).getTime());
                } catch (Exception e) {
                    log.info(e);
                }
                assert startTime != null;
                Timestamp endTime =
                        new Timestamp(startTime.getTime() + 24*60*60*1000);

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(
                                "createAt"),startTime),
                        criteriaBuilder.lessThan(root.get("createAt"),endTime)
                );
            }
        };
    }
}
