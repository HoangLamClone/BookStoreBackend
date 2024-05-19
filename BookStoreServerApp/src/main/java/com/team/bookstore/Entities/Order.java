package com.team.bookstore.Entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends  Auditable{
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    @Column(name = "customer_id")
    int customerId;
    String fullname;
    String order_note;
    int price;
    int total_dis;
    int status_trans;
    int total_price;
    String address;
    @JsonManagedReference("order")
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<Order_Detail> order_detail = new HashSet<>();
    @OneToOne(mappedBy =  "order" ,cascade = CascadeType.ALL)
    Payment payment;
}
