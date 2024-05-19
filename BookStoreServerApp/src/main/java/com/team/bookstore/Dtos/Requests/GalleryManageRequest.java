package com.team.bookstore.Dtos.Requests;

import com.team.bookstore.Entities.Book;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GalleryManageRequest {
    @NotNull
    int book_id;
    @NotNull
    String thumbnail;
    String description;
}
