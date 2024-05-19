package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "gallery_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GalleryManage extends Auditable{
    @Id
    @Column(name = "id_gallery")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    byte[] thumbnail;
    String description;
    @JsonBackReference("book")
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    Book book;
}
