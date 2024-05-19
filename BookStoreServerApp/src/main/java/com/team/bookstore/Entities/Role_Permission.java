package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team.bookstore.Entities.ComposeKey.RolePermissionKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role_Permission {
    @EmbeddedId
    RolePermissionKey id = new RolePermissionKey();
    @JsonBackReference("role")
    @ManyToOne
    @MapsId("rolename")
    @JoinColumn(name = "rolename")
    Role role;
    @JsonBackReference("permission")
    @ManyToOne
    @MapsId("permissionname")
    @JoinColumn(name = "permissionname")
    Permission permission;
}
