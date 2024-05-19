package com.team.bookstore.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerInformation extends Auditable{
    @Id
    @Column(name = "customer_id")
    int id;
    String fullname;
    String email;
    Boolean gender;
    Date birthday;
    String phonenumber;
    String address;
    byte[] avatar;
    boolean isvip;
    @JsonManagedReference("customer")
    @OneToMany(mappedBy = "customer_information",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    Set<Customer_Book> customer_book = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "customer_id")
    User user;
    @OneToMany(mappedBy = "customer_information",cascade = CascadeType.REMOVE)
    Set<Feedback> feedback;
}
