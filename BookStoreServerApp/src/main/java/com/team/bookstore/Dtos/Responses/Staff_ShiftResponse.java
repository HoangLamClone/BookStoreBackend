package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.Shift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff_ShiftResponse {
    int staff_id;
    String fullname;
    Shift shift;
    boolean hasWorkThisShift;
}
