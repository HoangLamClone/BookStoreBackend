package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Dtos.Responses.ChapterResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.Chapter;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.ChapterMapper;
import com.team.bookstore.Repositories.BookRepository;
import com.team.bookstore.Repositories.ChapterRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ChapterService {
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    BookRepository bookRepository;
    @Secured("ROLE_ADMIN")
    public List<ChapterResponse> getAllChapters(){
        try{
            return chapterRepository.findAll().stream().map(chapterMapper::toChapterResponse).collect(Collectors.toList());
        } catch (Exception e) {
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public ChapterResponse addChapter(MultipartFile file, Chapter chapter){
        try{
            if(!bookRepository.existsById(chapter.getBookId())){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Book existBook = bookRepository.findBookById(chapter.getBookId());
            if(file.isEmpty() || !existBook.getIsebook()){
                throw new ApplicationException(ErrorCodes.INVALID_OBJECT);
            }
            if(chapterRepository.existsChapterByChapterIndexAndBookId(chapter.getChapterIndex(),chapter.getBookId())){
                throw new ApplicationException(ErrorCodes.OBJECT_HAS_BEEN_EXISTING);
            }
            chapter.setSourcefile(file.getBytes());
            chapter.setBook(existBook);
            return chapterMapper.toChapterResponse(chapterRepository.save(chapter));
        } catch (Exception e) {
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public ChapterResponse updateChapter(int chapter_id,MultipartFile file,
                                         Chapter chapter){
        try{
            if(!chapterRepository.existsById(chapter_id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            if(!bookRepository.existsById(chapter.getBookId())){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Book existBook = bookRepository.findBookById(chapter.getBookId());
            if(file.isEmpty() || !existBook.getIsebook()){
                throw new ApplicationException(ErrorCodes.INVALID_OBJECT);
            }
            chapter.setId(chapter_id);
            chapter.setSourcefile(file.getBytes());
            chapter.setBook(existBook);
            return chapterMapper.toChapterResponse(chapterRepository.save(chapter));
        } catch (Exception e) {
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public ChapterResponse deleteChapter(int chapter_id){
        try{
            if(!chapterRepository.existsById(chapter_id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Chapter existChapter =
                    chapterRepository.findChapterById(chapter_id);
            chapterRepository.delete(existChapter);
            return chapterMapper.toChapterResponse(existChapter);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
