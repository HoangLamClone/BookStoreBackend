package com.team.bookstore.Dtos.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    int id;
    int  customerId;
    int  order_id;
    int  method_payment;
    boolean  payment_status;
    Date createAt;
}
