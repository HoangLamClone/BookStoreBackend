package com.team.bookstore.Services;

import com.nimbusds.jose.proc.SecurityContext;
import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Dtos.Responses.ProviderResponse;
import com.team.bookstore.Entities.*;
import com.team.bookstore.Entities.ComposeKey.BookAuthorKey;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.BookMapper;
import com.team.bookstore.Repositories.*;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.BookSpecification.GenerateBookKeywordSpec;
import static com.team.bookstore.Specifications.Customer_BookSpecification.CreateCustomerBookByCustomerIDSpec;

@Service
@Log4j2
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    GalleryManageRepository galleryManageRepository;
    @Autowired
    Customer_BookRepository customerBookRepository;
    @Autowired
    CustomerInformationRepository customerInformationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Book_AuthorRepository bookAuthorRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Secured("ROLE_ADMIN")
    public BookResponse addBook(MultipartFile sourceFile,Book book){
        try {
            if(book.getIsebook() && sourceFile.isEmpty()){
                throw  new ApplicationException(ErrorCodes.NULL_FIELD);
            }
            if(!sourceFile.isEmpty()  && book.getIsebook()){
                book.setSourcefile(sourceFile.getBytes());
                book.setReadingsession(0);
            }
            if(!book.getIsebook()){
                book.setSourcefile(null);
                book.setIsvip(null);
            }
            book.setLanguage(languageRepository.findLanguageById(book.getLanguage().getId()));
            book.setProvider(providerRepository.findProviderById(book.getProvider().getId()));
            book.setPublisher(publisherRepository.findPublisherById(book.getPublisher().getId()));
            Set<GalleryManage> galleryManages = book.getGalleryManage();
            Set<GalleryManage> newGalleries = galleryManages.stream().map(galleryManage -> {
                GalleryManage existGalleryManage =
                        galleryManageRepository.findGalleryManageById(galleryManage.getId());
                if(existGalleryManage==null){
                    throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                }
                return existGalleryManage;
            }).collect(Collectors.toSet());
            book.setGalleryManage(newGalleries);
            Book savedBook = Create_Book_Author_Relation_And_Save(book,false);
            return bookMapper.toBookResponse(savedBook);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    public List<BookResponse> getAllBook(){
        try {
            return bookRepository.findAllByIsebook(false).stream().map(bookMapper::toBookResponse).collect(Collectors.toList());
        }catch (Exception e){
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<BookResponse> getMyBooks(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            String username = authentication.getName();
            int customer_id =
                    userRepository.findUsersByUsername(username).getId();
            Specification<Customer_Book> spec =
                    CreateCustomerBookByCustomerIDSpec(customer_id);
            log.info("here");
            List<Book> boughtBooksForID =
                    customerBookRepository.findAll(spec).stream().map(customerBook -> {
                        try{
                            log.info("id: ");
                            log.info(bookRepository.findBookById(customerBook.getId().getBook_id()));
                            return bookRepository.findBookById(customerBook.getId().getBook_id());
                        } catch (Exception e){
                            log.info(e);
                            throw new ApplicationException(ErrorCodes.NOT_FOUND);
                        }
                    }).toList();
            return boughtBooksForID.stream().map(bookMapper::toBookResponse).collect(Collectors.toList());

        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public BookResponse updateABook(int id,MultipartFile sourceFile,
                                    Book updateContent){
        try{
        Book updateBook = bookRepository.findBookById(id);
            if(updateContent.getIsebook() && sourceFile.isEmpty()){
                throw  new ApplicationException(ErrorCodes.NULL_FIELD);
            }
            if(!sourceFile.isEmpty()  && updateContent.getIsebook()){
                updateBook.setSourcefile(sourceFile.getBytes());
            }
            if(!updateContent.getIsebook()){
                updateBook.setSourcefile(null);
                updateBook.setIsvip(null);
            }
        if (updateBook == null) {
            throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST
            );
        }
            updateBook = bookMapper.toBook(updateContent);
            updateBook.setCategory(categoryRepository.findCategoryById(updateBook.getCategory().getId()));
            updateBook.setId(id);
            updateBook.setLanguage(languageRepository.findLanguageById(updateBook.getLanguage().getId()));
            updateBook.setProvider(providerRepository.findProviderById(updateBook.getProvider().getId()));
            updateBook.setPublisher(publisherRepository.findPublisherById(updateBook.getPublisher().getId()));
            Book savedBook = Create_Book_Author_Relation_And_Save(updateBook,
                    true);
            return bookMapper.toBookResponse(savedBook);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    @Transactional
    public BookResponse deleteBook(int id){
        Book deleteBook = bookRepository.findBookById(id);
        if(deleteBook == null){
            throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
        }
        try {
            bookRepository.delete(deleteBook);
            return bookMapper.toBookResponse(deleteBook);
        }catch(Exception e){
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
    public List<BookResponse> findBooks(String keyword){
        try{
            Specification<Book> spec = GenerateBookKeywordSpec(keyword);
            log.info(bookRepository.findAll(spec));
            return bookRepository.findAll(spec).stream().map(bookMapper::toBookResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public Book Create_Book_Author_Relation_And_Save(Book book,
                                                     boolean isUpdate) {
        book.setTotal_pay(book.getPrice()-book.getDiscount());
        Set<Book_Author> book_authors = book.getBook_author().stream()
                .map(book_author -> {
                        Author author = authorRepository.findAuthorById(book_author.getAuthor().getId());
                        if (author == null) {
                            throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                        }
                        Book_Author new_book_author = new Book_Author();
                        new_book_author.setBook(book);
                        new_book_author.setAuthor(author);
                        return new_book_author;
                }).collect(Collectors.toSet());

        if(!isUpdate) {
            book.getBook_author().clear();
            book.getBook_author().addAll(book_authors);
        } else{
            List<Book_Author> existBook_Authors =
                    bookAuthorRepository.findBook_AuthorsByBook(book);
            List<Integer> exist_author_ids =
                    existBook_Authors.stream().map(existBook_Author->existBook_Author.getAuthor().getId()).toList();
            List<Integer> author_ids =
                    book_authors.stream().map(bookAuthor -> bookAuthor.getAuthor().getId()).toList();
           existBook_Authors.removeIf(existBook_Author-> !author_ids.contains(existBook_Author.getAuthor().getId()));
           for(Integer author_id:author_ids){
               if(!exist_author_ids.contains(author_id)){
                   book_authors.forEach(book_author->{
                       if(book_author.getAuthor().getId() == author_id){
                           existBook_Authors.add(book_author);
                       }
                   });
               }
           }
           book.getBook_author().clear();
           book.getBook_author().addAll(existBook_Authors);
        }
        return bookRepository.save(book);
    }

}
