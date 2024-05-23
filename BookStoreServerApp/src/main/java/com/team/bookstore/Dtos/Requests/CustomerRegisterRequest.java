package com.team.bookstore.Dtos.Requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRegisterRequest {
    String username;
    String password;
    String repassword;
    String email;
    String phonenumber;
    String fullname;
}
