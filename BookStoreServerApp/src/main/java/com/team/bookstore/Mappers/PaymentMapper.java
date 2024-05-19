package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Responses.PaymentResponse;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.Order;
import com.team.bookstore.Entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "order_id",source = "order",qualifiedByName =
            "toOrder_id")
    PaymentResponse toPaymentResponse(Payment payment);
    @Named("toOrder_id")
    default int toOrder_id(Order order){
        return order.getId();
    }


}
