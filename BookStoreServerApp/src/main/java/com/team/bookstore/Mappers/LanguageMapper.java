package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.LanguageRequest;
import com.team.bookstore.Dtos.Responses.LanguageResponse;
import com.team.bookstore.Entities.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.NoComplexMapping;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "book",ignore = true)
    Language toLanguage(LanguageRequest languageRequest);
    LanguageResponse toLanguageResponse(Language language);
}
