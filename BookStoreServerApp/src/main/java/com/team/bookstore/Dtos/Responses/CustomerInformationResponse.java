package com.team.bookstore.Dtos.Responses;

import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerInformationResponse {
    int id;
    String fullname;
    String email;
    Boolean gender;
    Date birthday;
    String phonenumber;
    String address;
    byte[] avatar;
    boolean isvip;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
}
