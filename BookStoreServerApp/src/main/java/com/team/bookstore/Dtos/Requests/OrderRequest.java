package com.team.bookstore.Dtos.Requests;

import com.team.bookstore.Entities.Order_Detail;
import com.team.bookstore.Entities.Payment;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotNull
    String fullname;
    @NotNull
    String order_note;
    @NotNull
    String address;
    Set<OrderDetailRequest> order_details;
    @NotNull
    @Max(2)
    @Min(0)
    int method_payment;
}

