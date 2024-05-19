package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.*;
import com.team.bookstore.Entities.ComposeKey.BookAuthorKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "book_author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book_Author {
    @EmbeddedId
    BookAuthorKey id = new BookAuthorKey();
    @JsonBackReference("book")
    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    Book book;
    @JsonBackReference("author")
    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("author_id")
    @JoinColumn(name = "author_id")
    Author author;
}
