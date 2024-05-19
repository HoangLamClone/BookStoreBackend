package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.KeywordRequest;
import com.team.bookstore.Dtos.Responses.KeywordResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.Book_Keyword;
import com.team.bookstore.Entities.Keyword;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface KeywordMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "book_keyword",source="book_ids",qualifiedByName =
            "toBook_Keyword")
    Keyword toKeyword(KeywordRequest keywordRequest);
    @Named("toBook_Keyword")
    default Set<Book_Keyword> toBook_Keyword(Set<Integer> book_ids){
        Set<Book_Keyword> book_keywords= new HashSet<>();
        book_ids.forEach(id->{
            Book_Keyword book_keyword =  new Book_Keyword();
            Book book = new Book();
            book.setId(id);
            book_keyword.setBook(book);
            book_keywords.add(book_keyword);
        });
        return book_keywords;
    }
    @Mapping(target = "books",source = "book_keyword",qualifiedByName =
            "toBooks")
    KeywordResponse toKeywordResponse(Keyword keyword);
    @Named("toBooks")
    default List<Book> toBooks(Set<Book_Keyword> book_keywords){
        List<Book> books = new ArrayList<>();
        book_keywords.forEach(bookKeyword -> {
            books.add(bookKeyword.getBook());
        });
        return books;
    }
}

