package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.FeedBackRequest;
import com.team.bookstore.Dtos.Responses.FeedbackResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "customer_information",ignore = true)
    @Mapping(target = "book",source = "book_id",qualifiedByName = "toBook")
    Feedback toFeedback(FeedBackRequest feedBackRequest);
    @Named("toCustomer_information")
    default CustomerInformation toCustomer_information(int customer_id){
        CustomerInformation customerInformation = new CustomerInformation();
        customerInformation.setId(customer_id);
        return customerInformation;
    }
    @Named("toBook")
    default Book toBook(int book_id){
        Book book = new Book();
        book.setId(book_id);
        return book;
    }
    @Mapping(target = "customer_id", source = "customer_information",
            qualifiedByName = "toCustomer_id")
    @Mapping(target = "fullname",source = "customer_information",
            qualifiedByName = "toFullname")
    FeedbackResponse toFeedbackResponse(Feedback feedback);
    @Named("toCustomer_id")
    default int toCustomer_id(CustomerInformation customerInformation){
        return customerInformation.getId();
    }
    @Named("toFullname")
    default int toFullname(CustomerInformation customerInformation){
        return customerInformation.getId();
    }

}
