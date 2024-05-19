package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Entity
@Table(name = "book_language")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Language extends Auditable{
    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String language_name;
    @JsonBackReference("book")
    @OneToMany(mappedBy = "language")
    @JsonIgnore
    Set<Book> book;
}
