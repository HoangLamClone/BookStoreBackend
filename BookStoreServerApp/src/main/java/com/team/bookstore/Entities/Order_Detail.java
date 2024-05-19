package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.bookstore.Entities.ComposeKey.OrderDetailKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order_Detail {
    @EmbeddedId
    OrderDetailKey id = new OrderDetailKey();
    String avatar;
    int status;
    int price;
    int quantity;
    int discount;
    int total_money = price * quantity - discount;
    Date create_at;
    Date update_at;
    @JsonBackReference("book")
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    Book book;
    @JsonBackReference("order")
    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;
}
