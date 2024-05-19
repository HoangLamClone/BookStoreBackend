package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProviderRequest {
    @NotNull
    String providername;
    @NotNull
    String address;
    @NotNull
    String representativename;
}
