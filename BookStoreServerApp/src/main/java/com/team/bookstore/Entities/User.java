package com.team.bookstore.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Auditable{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "user_id")
    int id;
    String username;
    String password;
    String token;
    @JsonManagedReference("user")
    @OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST,fetch =
            FetchType.EAGER)
    Set<User_Role> user_role = new HashSet<>();
    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL)
    CustomerInformation customer_information;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    StaffInformation staff_information;
    @JsonManagedReference("sender")
    @OneToMany(mappedBy = "sender",fetch = FetchType.EAGER)
    Set<Message> send_messages;
    @JsonManagedReference("receiver")
    @OneToMany(mappedBy = "receiver",fetch = FetchType.EAGER)
    Set<Message> receive_messages;
}
