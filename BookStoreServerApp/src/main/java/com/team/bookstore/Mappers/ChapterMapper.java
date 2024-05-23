package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.ChapterRequest;
import com.team.bookstore.Dtos.Responses.ChapterResponse;
import com.team.bookstore.Entities.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "sourcefile",ignore = true)
    Chapter toChapter(ChapterRequest chapterRequest);
    ChapterResponse toChapterResponse(Chapter chapter);
}
