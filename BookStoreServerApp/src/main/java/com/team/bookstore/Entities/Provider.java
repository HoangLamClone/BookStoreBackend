package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Entity
@Table(name = "provider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Provider extends Auditable{
    @Id
    @Column(name = "provider_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String providername;
    String address;
    String representativename;
    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    Set<Book> book;
}
