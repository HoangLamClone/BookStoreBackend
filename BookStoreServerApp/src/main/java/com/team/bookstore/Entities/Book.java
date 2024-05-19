package com.team.bookstore.Entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends Auditable{
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    String title;
    short num_pages;
    Date publication_date;
    float bookQuantity;
    int price;
    int discount;
    String description;
    int hot;
    int total_pay;
    int available;
    Boolean isebook;
    Boolean isvip;
    byte[] sourcefile;
    Integer readingsession;
    @JsonManagedReference("book")
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,
            orphanRemoval=true,
            fetch = FetchType.EAGER)
    Set<Book_Author> book_author = new HashSet<>();
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval=true)
    Set<Book_Keyword> book_keyword = new HashSet<>();
    @OneToMany(mappedBy = "book")
    @JsonIgnore
    Set<Import_Detail> import_detail = new HashSet<>();
    @OneToMany(mappedBy = "book")
    @JsonIgnore
    Set<Order_Detail> order_detail = new HashSet<>();
    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Customer_Book> customer_book = new HashSet<>();
    @OneToMany(mappedBy = "book")
    Set<GalleryManage> galleryManage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_id")
    Provider provider;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    Language language;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    Publisher publisher;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    Category category;
    @OneToMany(mappedBy = "book",cascade = CascadeType.REMOVE,fetch =
            FetchType.EAGER)
    Set<Feedback> feedback = new HashSet<>();
}
