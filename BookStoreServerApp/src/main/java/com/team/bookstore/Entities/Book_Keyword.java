package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team.bookstore.Entities.ComposeKey.BookKeywordKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "book_keyword")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book_Keyword {
    @EmbeddedId
    BookKeywordKey id = new BookKeywordKey();
    @JsonBackReference("book")
    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    Book book;
    @JsonBackReference("keyword")
    @ManyToOne
    @MapsId("keyword_id")
    @JoinColumn(name = "keyword_id")
    Keyword keyword;
}
