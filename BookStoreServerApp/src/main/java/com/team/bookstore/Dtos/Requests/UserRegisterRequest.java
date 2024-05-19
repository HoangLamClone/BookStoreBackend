package com.team.bookstore.Dtos.Requests;

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
public class UserRegisterRequest {
    String username;
    String password;
    Set<Role> roles;
    Date createdTime;
}
