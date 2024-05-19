package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Responses.RevenueDayResponse;
import com.team.bookstore.Entities.RevenueDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RevenueMapper {
    RevenueDayResponse toRevenueDayResponse(RevenueDay revenueDay);
}
