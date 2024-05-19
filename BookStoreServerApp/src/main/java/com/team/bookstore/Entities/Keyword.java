package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "keyword")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Keyword extends Auditable{
    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String name;
    String description;
    int status;
    int hot;
    @JsonManagedReference("keyword")
    @OneToMany(mappedBy = "keyword",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<Book_Keyword> book_keyword = new HashSet<>();
}
