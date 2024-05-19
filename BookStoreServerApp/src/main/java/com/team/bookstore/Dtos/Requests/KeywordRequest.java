package com.team.bookstore.Dtos.Requests;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeywordRequest {
    @NotNull
    String       name;
    String       description;
    @NotNull
    @Max(10)
    int          status;
    @Max(10)
    int          hot;
    @NotNull
    Set<Integer> book_ids;
}
