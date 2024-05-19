package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserphoneLoginRequest {
    @NotNull
    @Digits(integer = 10,fraction = 0)
    String phonenumber;
    @NotNull
    @Size(max = 16,min = 12)
    String password;
}
