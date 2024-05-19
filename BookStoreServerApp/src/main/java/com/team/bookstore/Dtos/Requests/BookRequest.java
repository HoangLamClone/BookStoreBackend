package com.team.bookstore.Dtos.Requests;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.bookstore.Entities.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    @NotNull
    String title;
    @Max(10000)
    short num_pages;
    Date publication_date;
    @NotNull
    float bookQuantity;
    @NotNull
    int price;
    int discount;
    String description;
    @Max(100)
    int hot;
    @NotNull
    int available;
    @NotNull
    int language_id;
    @NotNull
    int category_id;
    @NotNull
    int publisher_id;
    @NotNull
    int provider_id;
    @NotNull
    boolean isebook;
    @NotNull
    boolean isvip;
    Set<Integer> gallery_ids;
    Set <Integer> author_ids;
}