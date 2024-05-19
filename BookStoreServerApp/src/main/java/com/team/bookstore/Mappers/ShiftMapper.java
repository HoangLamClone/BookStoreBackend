package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.ShiftRequest;
import com.team.bookstore.Dtos.Responses.ShiftResponse;
import com.team.bookstore.Entities.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "staff_shift",ignore = true)
    Shift toShift(ShiftRequest shiftRequest);
    ShiftResponse toShiftResponse(Shift shift);
}
