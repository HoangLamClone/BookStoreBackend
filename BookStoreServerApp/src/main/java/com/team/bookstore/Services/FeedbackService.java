package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Dtos.Responses.FeedbackResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.Feedback;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.FeedbackMapper;
import com.team.bookstore.Repositories.BookRepository;
import com.team.bookstore.Repositories.CustomerInformationRepository;
import com.team.bookstore.Repositories.FeedbackRepository;
import com.team.bookstore.Repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.FeedBackSpecification.CreateFeedbackKeywordSpec;

@Service
@Log4j2
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    CustomerInformationRepository customerInformationRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    public List<FeedbackResponse> getAllFeedbacks(){
        try{
            return feedbackRepository.findAll().stream().map(feedbackMapper::toFeedbackResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<FeedbackResponse> findFeedBacksBy(String keyword){
        try{
            Specification<Feedback> spec = CreateFeedbackKeywordSpec(keyword);
            return feedbackRepository.findAll(spec).stream().map(feedbackMapper::toFeedbackResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }

    public FeedbackResponse createFeedback(Feedback feedback) {
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            if(!bookRepository.existsById(feedback.getBook().getId()) || !userRepository.existsByUsername(authentication.getName())){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            int customer_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            CustomerInformation customer =
                    customerInformationRepository.findCustomerInformationById(customer_id);
            Book book = bookRepository.findBookById(feedback.getBook().getId());
            feedback.setCustomer_information(customer);
            feedback.setBook(book);
            return feedbackMapper.toFeedbackResponse(feedbackRepository.save(feedback));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public FeedbackResponse deleteFeedback(int id){
        try{
            if(!feedbackRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Feedback exsitFeedback = feedbackRepository.findFeedbackById(id);
            feedbackRepository.delete(exsitFeedback);
            return feedbackMapper.toFeedbackResponse(exsitFeedback);
        }catch(Exception e){
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
