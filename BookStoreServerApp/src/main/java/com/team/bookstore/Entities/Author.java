package com.team.bookstore.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author extends Auditable{
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String author_name;
    @JsonManagedReference("author")
    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Book_Author> book_author = new HashSet<>();
}

