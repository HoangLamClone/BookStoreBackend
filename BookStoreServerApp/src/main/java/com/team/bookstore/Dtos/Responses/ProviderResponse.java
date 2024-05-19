package com.team.bookstore.Dtos.Responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderResponse {
    int id;
    String providername;
    String address;
    String representativename;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
}
