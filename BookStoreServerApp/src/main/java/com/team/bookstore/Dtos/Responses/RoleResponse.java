package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Permission;
import com.team.bookstore.Entities.Role_Permission;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String rolename;
    String description;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
    Set<Role_Permission> role_permission;
}
