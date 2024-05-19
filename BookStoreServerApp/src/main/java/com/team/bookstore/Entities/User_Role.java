package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team.bookstore.Entities.ComposeKey.UserRoleKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User_Role {
    @EmbeddedId
    UserRoleKey id =new UserRoleKey();
    @JsonBackReference("user")
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    User user;
    @JsonBackReference("role")
    @ManyToOne
    @MapsId("rolename")
    @JoinColumn(name = "rolename")
    Role role;
}
