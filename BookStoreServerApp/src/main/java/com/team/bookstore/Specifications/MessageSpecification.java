package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
@Log4j2
public class MessageSpecification {
    public static Specification<Message> CreateMessageKeywordSpec(String keyword){
        return new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("id").as(String.class),
                                likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(
                                "message_content")),likeKeyword),
                        criteriaBuilder.like(root.get(
                                "message_status").as(String.class),
                                likeKeyword),
                        criteriaBuilder.like(root.get("sender").get("id").as(String.class),likeKeyword),
                        criteriaBuilder.like(root.get("receiver").get("id").as(String.class),likeKeyword)
                );
            }
        };
    }
    public static Specification<Message> CreateMessageSenderReceiverSpec(int sender_id, int receiver_id){
        return new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.or(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("sender").get("id"),
                                    sender_id),
                            criteriaBuilder.equal(root.get("receiver").get(
                                    "id"),
                                    receiver_id)
                    ),
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("sender").get("id"),
                                    receiver_id),
                            criteriaBuilder.equal(root.get("receiver").get(
                                    "id"),
                                    sender_id)
                    )
                );
            }
        };
    }
    public static Specification<Message> CreateMessageSenderSpec(int sender_id){
        return new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("sender").get("id"),
                        sender_id);
            }
        };
    }
}
