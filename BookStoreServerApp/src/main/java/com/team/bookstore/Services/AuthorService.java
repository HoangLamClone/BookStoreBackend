package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Requests.AuthorRequest;
import com.team.bookstore.Dtos.Responses.AuthorResponse;
import com.team.bookstore.Entities.Author;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.AuthorMapper;
import com.team.bookstore.Repositories.AuthorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.AuthorSpecification.GenerateAuthorKeywordSpec;
@Service
@Log4j2
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorMapper authorMapper;
    @Secured("ROLE_ADMIN")
    public AuthorResponse createAuthor(AuthorRequest authorRequest){
        Author savedAuthor =
                authorRepository.save(authorMapper.toAuthor(authorRequest));
        return authorMapper.toAuthorResponse(savedAuthor);
    }
    @Secured("ROLE_ADMIN")
    public AuthorResponse updateAuthor(int id, Author author){
        try{
            if(!authorRepository.existsAuthorById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            author.setId(id);
            Author savedAuthor = authorRepository.save(author);
            return authorMapper.toAuthorResponse(savedAuthor);
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_AUTHORISED);
        }
    }
    public List<AuthorResponse> getAllAuthor(){
        try {
            return authorRepository.findAll().stream().map(authorMapper::toAuthorResponse)
                    .collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<AuthorResponse> findAuthorBy(String keyword){
        try{
            Specification<Author> spec = GenerateAuthorKeywordSpec(keyword);
            return authorRepository.findAll(spec).stream().map(authorMapper::toAuthorResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
        }
    }
    @Secured("ROLE_ADMIN")
    public AuthorResponse deleteAuthor(int id){
        try{
            if(!authorRepository.existsAuthorById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Author existAuthor = authorRepository.findAuthorById(id);
            authorRepository.delete(existAuthor);
            return authorMapper.toAuthorResponse(existAuthor);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_AUTHORISED);
        }

    }
}
