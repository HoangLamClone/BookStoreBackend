package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.OrderDetailRequest;
import com.team.bookstore.Dtos.Requests.OrderRequest;
import com.team.bookstore.Dtos.Responses.OrderResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.Order;
import com.team.bookstore.Entities.Order_Detail;
import com.team.bookstore.Entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.parameters.P;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "status_trans",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "price",ignore = true)
    @Mapping(target = "total_dis",ignore = true)
    @Mapping(target = "total_price",ignore = true)
    @Mapping(target = "order_detail",source = "order_details",
            qualifiedByName = "toOrderDetail")
    @Mapping(target = "payment",source = "method_payment",qualifiedByName =
            "toPayment")
    @Mapping(target = "customerId",ignore = true)

    Order toOrder(OrderRequest orderRequest);
    @Named("toOrderDetail")
    default Set<Order_Detail> toOrderDetail(Set<OrderDetailRequest> order_details){
        Set<Order_Detail> new_order_details = new HashSet<>();
        order_details.forEach(order_detail -> {
            Order_Detail orderDetail = new Order_Detail();
            Book book = new Book();
            book.setId(order_detail.getBook_id());
            orderDetail.setBook(book);
            orderDetail.setDiscount(book.getDiscount() * order_detail.getQuantity());
            orderDetail.setQuantity(order_detail.getQuantity());
            new_order_details.add(orderDetail);
        });
        return new_order_details;
    }
    @Named("toPayment")
    default Payment toPayment(int method_payment){
        Payment payment = new Payment();
        payment.setMethod_payment(method_payment);
        payment.setPayment_status(false);
        return payment;
    }
    @Mapping(target = "method_payment",source = "payment",qualifiedByName =
            "toMethod_payment")
    OrderResponse toOrderResponse(Order order);
    @Named("toMethod_payment")
    default int toMethod_payment(Payment payment){
        return payment.getMethod_payment();
    }
}
