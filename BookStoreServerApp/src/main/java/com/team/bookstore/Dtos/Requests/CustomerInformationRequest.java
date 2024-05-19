package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerInformationRequest {
    @NotNull
    @Size(max = 100)
    String fullname;
    @Email
    @NotNull
    String email;
    @NotNull
    Boolean gender;
    @NotNull
    Date birthday;
    @NotNull
    @Digits(integer = 10,fraction = 0)
    String phonenumber;
    @NotNull
    String address;
}
