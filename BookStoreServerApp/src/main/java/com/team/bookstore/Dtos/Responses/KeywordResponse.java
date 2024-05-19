package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Book;
import jakarta.persistence.Access;
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
public class KeywordResponse {
    String name;
    String description;
    int status;
    int hot;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
    List<Book> books;
}
