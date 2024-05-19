package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff_ShiftRequest {
    @NotNull
    int staff_id;
    @NotNull
    int shift_id;
    @BooleanFlag
    boolean hasWorkThisShift;
}
