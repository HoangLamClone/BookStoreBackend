package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Order_Detail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    int id;
    int customerId;
    String fullname;
    String order_note;
    int price;
    int total_dis;
    int status_trans;
    int total_price;
    String address;
    List<Order_Detail> order_detail;
    int method_payment;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
}
