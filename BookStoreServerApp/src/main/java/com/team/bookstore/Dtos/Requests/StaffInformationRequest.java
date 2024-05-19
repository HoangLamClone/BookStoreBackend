package com.team.bookstore.Dtos.Requests;

import io.micrometer.common.lang.NonNullFields;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffInformationRequest{
    @NotNull
    String fullname;
    @NotNull
    @BooleanFlag
    Boolean gender;
    @NotNull
    @DateTimeFormat
    Date birthday;
    @NotNull
    @Digits(integer = 10,fraction = 0)
    String phonenumber;
    @NotNull
    @Email
    String email;
    @NotNull
    String address;
    @NotNull
    @DateTimeFormat
    Date initiate_time;
    @Digits(integer = 10000,fraction = 0)
    int salary;
}
