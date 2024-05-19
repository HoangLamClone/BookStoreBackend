package com.team.bookstore.Services;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.team.bookstore.Dtos.Responses.KeywordResponse;
import com.team.bookstore.Entities.*;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.KeywordMapper;
import com.team.bookstore.Repositories.BookRepository;
import com.team.bookstore.Repositories.KeywordRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.KeywordSpecification.CreateKeywordKeywordSpec;

@Service
@Log4j2
public class KeywordService {

    @Autowired
    KeywordRepository keywordRepository;
    @Autowired
    KeywordMapper keywordMapper;
    @Autowired
    BookRepository bookRepository;
    public List<KeywordResponse> getAllKeywords(){
        try{
            return keywordRepository.findAll().stream().map(keywordMapper::toKeywordResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<KeywordResponse> findKeywordsBy(String keyword){
        try{
            Specification<Keyword> spec = CreateKeywordKeywordSpec(keyword);
            return keywordRepository.findAll(spec).stream().map(keywordMapper::toKeywordResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public KeywordResponse createKeyword(Keyword keyword){
        try{
            Keyword savedKeyword =
                    Create_Book_Keyword_Relation_And_Save(keyword);
            return keywordMapper.toKeywordResponse(savedKeyword);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    public KeywordResponse updateKeyword(int id,Keyword keyword){
        try{
            if(!keywordRepository.existsByid(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            keyword.setId(id);
            Keyword savedKeyword =
                    Create_Book_Keyword_Relation_And_Save(keyword);
            return keywordMapper.toKeywordResponse(savedKeyword);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public KeywordResponse deleteKeyword(int id){
        try{
            if(!keywordRepository.existsByid(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Keyword existKeyword = keywordRepository.findKeywordById(id);
            keywordRepository.delete(existKeyword);
            return keywordMapper.toKeywordResponse(existKeyword);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }

    public Keyword Create_Book_Keyword_Relation_And_Save(Keyword keyword) {
        Set<Book_Keyword> book_keywords = keyword.getBook_keyword().stream()
                .map(book_keyword -> {
                    Book book =
                            bookRepository.findBookById(book_keyword.getBook().getId());
                    if (book == null) {
                        throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                    }
                    Book_Keyword new_book_keyword = new Book_Keyword();
                    new_book_keyword.setKeyword(keyword);
                    new_book_keyword.setBook(book);
                    return new_book_keyword;
                }).collect(Collectors.toSet());
        keyword.getBook_keyword().clear();
        keyword.getBook_keyword().addAll(book_keywords);
        return keywordRepository.save(keyword);
    }


}
