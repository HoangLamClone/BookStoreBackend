package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
    Set<Role> roles;
}
