package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.ComposeKey.CustomerBookKey;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.Customer_Book;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.BookMapper;
import com.team.bookstore.Repositories.BookRepository;
import com.team.bookstore.Repositories.CustomerInformationRepository;
import com.team.bookstore.Repositories.Customer_BookRepository;
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

import static com.team.bookstore.Specifications.Customer_BookSpecification.CreateCustomerBookByCustomerIDSpec;

@Service
@Log4j2
public class EBookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    CustomerInformationRepository customerInformationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Customer_BookRepository customerBookRepository;
    public List<BookResponse> getAllEBook(){
        try{
            return bookRepository.findAllByIsebook(true).stream().map(bookMapper::toBookResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    private void increaseReadingSesion(int book_id){
        try{
            Book book = bookRepository.findBookById(book_id);
            book.setReadingsession(book.getReadingsession() + 1);
            bookRepository.save(book);
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }

    }
    @Secured("ROLE_CUSTOMER")
    public byte[] readEBook(int book_id){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            int customerId =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            if(!bookRepository.existsById(book_id)){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            Book          book          = bookRepository.findBookById(book_id);
            if(!book.getIsebook()){
                throw new ApplicationException(ErrorCodes.NOT_FOUND);
            }
            if(book.getTotal_pay()==0){
                increaseReadingSesion(book_id);
                return book.getSourcefile();

            }
            CustomerBookKey customerBookKey = new CustomerBookKey();
            customerBookKey.setCustomer_id(customerId);
            customerBookKey.setBook_id(book_id);

            if(!customerBookRepository.existsCustomer_BookById(customerBookKey)){
                throw new ApplicationException(ErrorCodes.UN_AUTHORISED);
            }
            increaseReadingSesion(book_id);
            return book.getSourcefile();
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_CATEGORIED);
        }
    }
    @Secured("ROLE_CUSTOMER")
    public List<BookResponse> getMyPurchasedEBooks(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           if(authentication == null){
               throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
           }
           int customerId =
                   userRepository.findUsersByUsername(authentication.getName()).getId();
           Specification<Customer_Book> spec =
                   CreateCustomerBookByCustomerIDSpec(customerId);
           List<Book> books =
                   customerBookRepository.findAll(spec).stream().map(Customer_Book::getBook
            ).toList();
           return books.stream().map(bookMapper::toBookResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_AUTHORISED);
        }
    }
}
