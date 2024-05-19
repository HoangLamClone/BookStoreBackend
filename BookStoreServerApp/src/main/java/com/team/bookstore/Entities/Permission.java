package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission extends Auditable{
    @Id
    String permissionname;
    String description;
    @JsonManagedReference("permission")
    @OneToMany(mappedBy = "permission",fetch = FetchType.EAGER)
    @JsonIgnore
    Set<Role_Permission> role_permission = new HashSet<>();

}
