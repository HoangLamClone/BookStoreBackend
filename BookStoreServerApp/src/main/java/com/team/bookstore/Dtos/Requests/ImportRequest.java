package com.team.bookstore.Dtos.Requests;

import com.team.bookstore.Entities.Import_Detail;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class  ImportRequest {
    @NotNull
    List<ImportDetailRequest> importDetailRequests;
}
