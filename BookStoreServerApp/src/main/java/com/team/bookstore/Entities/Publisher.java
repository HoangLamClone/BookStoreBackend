package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Publisher extends Auditable{
    @Id
    @Column(name = "publisher_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String publisher_name;
    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    Set<Book> book;
}
