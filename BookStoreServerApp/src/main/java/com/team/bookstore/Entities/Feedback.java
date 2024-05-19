package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback extends Auditable{
    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String feedback_comment;
    int rating;
    @JsonBackReference("customer_information")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    CustomerInformation customer_information;
    @JsonBackReference("book")
    @ManyToOne
    @JoinColumn(name = "book_id",insertable=false, updatable=false)
    Book book;
}
