package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Permission;
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
public class StaffInformationResponse {
    int id;
    String fullname;
    Boolean gender;
    Date birthday;
    String phonenumber;
    String address;
    Date initiate_time;
    int salary;
    byte[] avatar;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
}
