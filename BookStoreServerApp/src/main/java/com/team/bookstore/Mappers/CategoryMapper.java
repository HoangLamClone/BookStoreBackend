package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.CategoryRequest;
import com.team.bookstore.Dtos.Responses.CategoryResponse;
import com.team.bookstore.Entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "book",ignore = true)
    @Mapping(target = "avatar",ignore = true)
    Category toCategory(CategoryRequest categoryRequest);
    CategoryResponse toCategoryResponse(Category category);
}
