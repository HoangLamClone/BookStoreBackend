package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.PublisherRequest;
import com.team.bookstore.Dtos.Responses.PublisherResponse;
import com.team.bookstore.Entities.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "book",ignore = true)
    Publisher toPublisher(PublisherRequest publisherRequest);
    PublisherResponse toPublisherResponse(Publisher publisher);
}
