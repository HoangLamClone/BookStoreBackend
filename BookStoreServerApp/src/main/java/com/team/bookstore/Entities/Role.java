package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends  Auditable{
    @Id
    String rolename;
    String description;
    @JsonManagedReference("role")
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    Set<User_Role> user_role = new HashSet<>();
    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL,fetch =
            FetchType.EAGER)
    @JsonIgnore
    Set<Role_Permission> role_permission = new HashSet<>();
}
