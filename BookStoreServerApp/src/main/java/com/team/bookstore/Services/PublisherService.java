package com.team.bookstore.Services;
import com.team.bookstore.Dtos.Responses.PublisherResponse;
import com.team.bookstore.Entities.Publisher;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.PublisherMapper;
import com.team.bookstore.Repositories.PublisherRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.team.bookstore.Specifications.PublisherSpecification.CreatePublisherKeywordSpec;

@Service
@Log4j2
public class PublisherService {
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    PublisherMapper publisherMapper;
    public List<PublisherResponse> getAllPublisher(){
        try {
            return publisherRepository.findAll().stream().map(publisherMapper::toPublisherResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<PublisherResponse> findPublisherBy(String keyword){
        try{
            Specification<Publisher> spec = CreatePublisherKeywordSpec(keyword);
            return publisherRepository.findAll(spec).stream().map(publisherMapper::toPublisherResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public PublisherResponse createPublisher(Publisher publisher){
        try{
            return publisherMapper.toPublisherResponse(publisherRepository.save(publisher));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public PublisherResponse updatePublisher(int id,Publisher publisher){
        try{
            if(!publisherRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            publisher.setId(id);
            return publisherMapper.toPublisherResponse(publisherRepository.save(publisher));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public PublisherResponse deletePublisher(int id){
        try{
            if(!publisherRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Publisher existPublisher =
                    publisherRepository.findPublisherById(id);
            publisherRepository.delete(existPublisher);
            return publisherMapper.toPublisherResponse(existPublisher);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
