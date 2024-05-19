package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends Auditable{
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String name;
    Short hot;
    byte[] avatar;
    @JsonBackReference("book")
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    Set<Book> book;
}
