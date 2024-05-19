package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.CustomerInformation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackResponse {
    int id;
    String feedback_comment;
    int rating;
    int customer_id;
    String fullname;
    Book book;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
}
