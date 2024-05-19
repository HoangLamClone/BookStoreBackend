package com.team.bookstore.Dtos.Requests;

import com.team.bookstore.Entities.Role_Permission;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotNull
    String rolename;
    @NotNull
    String description;
    @NotNull
    Set<Role_Permission> role_permission;
}
