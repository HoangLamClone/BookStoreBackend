package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Import_Detail;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportResponse {
    int id;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
    List<Import_Detail> import_detail;
    int import_total;
    boolean import_status;
}
