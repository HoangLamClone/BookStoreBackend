package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportDetailRequest {
    @NotNull
    int book_id;
    @NotNull
    @Max(10000)
    int quantity;
    @NotNull
    int import_cost;
}
