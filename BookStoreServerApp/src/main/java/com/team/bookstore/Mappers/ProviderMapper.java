package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.ProviderRequest;
import com.team.bookstore.Dtos.Responses.ProviderResponse;
import com.team.bookstore.Entities.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProviderMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "book",ignore = true)
    Provider toProvider(ProviderRequest providerRequest);
    ProviderResponse toProviderResponse(Provider provider);
}
