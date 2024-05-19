package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    @NotNull
    int book_id;
    @Max(10000)
    int quantity;
}
