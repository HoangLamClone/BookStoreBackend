package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.GalleryManageRequest;
import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Dtos.Responses.GalleryManageResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.GalleryManage;
import com.team.bookstore.Repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface GalleryManageMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target =  "updateBy",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "book",source = "book_id",qualifiedByName = "toBook")
    @Mapping(target = "thumbnail",ignore = true)
    GalleryManage toGalleryManage(GalleryManageRequest galleryManageRequest);
    @Mapping(target="book_id",source="book",qualifiedByName = "toBook_id")
    GalleryManageResponse toGalleryManageResponse(GalleryManage galleryManage);
    @Named("toBook")
    default Book toBook(int book_id){
        Book book = new Book();
        book.setId(book_id);
        return book;
    }

    @Named("toBook_id")
    default int toBook_id(Book book){
        return book.getId();
    }
}
