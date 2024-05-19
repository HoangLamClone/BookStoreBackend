package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.LanguageResponse;
import com.team.bookstore.Entities.Language;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.LanguageMapper;
import com.team.bookstore.Repositories.LanguageRepository;
import com.team.bookstore.Specifications.LanguageSpecification;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.LanguageSpecification.CreateLanguageKeywordSpec;

@Service
@Log4j2
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    LanguageMapper languageMapper;
    public List<LanguageResponse> getAllLanguage(){
        try {
            return languageRepository.findAll().stream().map(languageMapper::toLanguageResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<LanguageResponse> findLanguagesBy(String keyword){
        try{
            Specification<Language> spec = CreateLanguageKeywordSpec(keyword);
            return languageRepository.findAll(spec).stream().map(languageMapper::toLanguageResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public LanguageResponse createLanguage(Language language){
        try{
            return languageMapper.toLanguageResponse(languageRepository.save(language));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public LanguageResponse updateLanguage(int id,Language language){
        try{
            if(!languageRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            language.setId(id);
            return languageMapper.toLanguageResponse(languageRepository.save(language));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public LanguageResponse deleteLanguage(int id){
        try{
            if(!languageRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Language existLanguage = languageRepository.findLanguageById(id);
            languageRepository.delete(existLanguage);
            return languageMapper.toLanguageResponse(existLanguage);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
