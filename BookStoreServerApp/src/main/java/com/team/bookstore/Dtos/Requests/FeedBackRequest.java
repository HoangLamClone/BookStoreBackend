package com.team.bookstore.Dtos.Requests;

import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.CustomerInformation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedBackRequest {
    @NotNull
    int book_id;
    @NotNull
    String feedback_comment;
    @NotNull
    int rating;
}
