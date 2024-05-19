package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Permission;
import jakarta.persistence.Access;
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
public class ShiftResponse {
    int id;
    Date start_time;
    Date end_time;
    String description;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
}
