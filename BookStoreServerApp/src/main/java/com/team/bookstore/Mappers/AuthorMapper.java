package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.AuthorRequest;
import com.team.bookstore.Dtos.Responses.AuthorResponse;
import com.team.bookstore.Entities.Author;
import com.team.bookstore.Entities.Book_Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;

import javax.xml.stream.events.StartDocument;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "book_author",ignore = true)
    Author toAuthor(AuthorRequest authorRequest);
    AuthorResponse toAuthorResponse(Author author);
    Author toAuthor(Author author);
}
