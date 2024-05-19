package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShiftRequest {
    @NotNull
    @DateTimeFormat
    Date start_time;
    @NotNull
    @DateTimeFormat
    Date end_time;
    String description;
}
