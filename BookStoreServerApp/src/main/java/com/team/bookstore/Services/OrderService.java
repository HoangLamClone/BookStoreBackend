package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.OrderResponse;
import com.team.bookstore.Entities.*;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.OrderMapper;
import com.team.bookstore.Repositories.*;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.OrderSpecification.CreateOrderKeywordSpec;

@Service
@Log4j2
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CustomerInformationRepository customerInformationRepository;
    @Autowired
    Customer_BookService customerBookService;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    UserRepository userRepository;
    public List<OrderResponse> getAllOrders(){
        try{
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
        } catch (Exception e){
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<OrderResponse> findAllOrdersBy(String keyword){
        try{
            Specification<Order> spec = CreateOrderKeywordSpec(keyword);
            return orderRepository.findAll(spec).stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
        } catch (Exception e){
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<OrderResponse> getMyOrder(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            int customer_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            return orderRepository.findOrdersByCustomerId(customer_id).stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Transactional
    public OrderResponse createOrder(Order order){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication==null){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            int customer_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            if(!customerInformationRepository.existsCustomerInformationById(customer_id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            order.setCustomerId(customer_id);
            Order savedOrder = Create_Order_Detail_Relation_And_Save(order);
            return orderMapper.toOrderResponse(savedOrder);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    public AtomicInteger Cal_Total_Discount(Set<Order_Detail> order_detail){
        AtomicInteger sum = new AtomicInteger();
        order_detail.forEach(orderDetail ->{
            sum.addAndGet(orderDetail.getDiscount());
        });
        return sum;
    }
    public AtomicInteger Cal_Total_Price(Set<Order_Detail> order_detail){
        AtomicInteger sum = new AtomicInteger();
        order_detail.forEach(orderDetail->{
            sum.addAndGet(orderDetail.getPrice());
        });
        return sum;
    }
    public OrderResponse updateOrder(int id, Order order){
         try{
             if(!orderRepository.existsById(id)){
                 throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
             }
             if(order.getStatus_trans()!=0){
                 throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
             }
             order.setId(id);
             Order updatedOrder = Create_Order_Detail_Relation_And_Save(order);
             return orderMapper.toOrderResponse(updatedOrder);
         } catch(Exception e){
             log.info(e);
             throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
         }
    }
    @Secured("ROLE_ADMIN")
    public OrderResponse verifyOrder(int id, int status){
        //0:unverified,1:accept,2:prepair,3:Delivery,4:Payment!
        try{
            if(!orderRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Order existOrder = orderRepository.findOrderById(id);
            if(existOrder.getStatus_trans() == 1){
                return orderMapper.toOrderResponse(existOrder);
            } else
            if(status ==1) {
                existOrder.setStatus_trans(1);
                existOrder.getOrder_detail().forEach(order_detail -> {
                    if(!bookRepository.existsById(order_detail.getBook().getId())){
                        throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                    }
                    Book existBook =
                            bookRepository.findBookById(order_detail.getBook().getId());
                    float newQuantity =
                            existBook.getBookQuantity() - order_detail.getQuantity();
                    existBook.setBookQuantity(newQuantity);
                    bookRepository.save(existBook);
                });
                customerBookService.updateCustomer_Book(existOrder.getCustomerId(), existOrder.getOrder_detail());
                return orderMapper.toOrderResponse(orderRepository.save(existOrder));
            } else {
                existOrder.setStatus_trans(status);
                return orderMapper.toOrderResponse(orderRepository.save(existOrder));
            }
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_VERIFY);
        }
    }
    public Order Create_Order_Detail_Relation_And_Save(Order order) {
        Set<Order_Detail> order_details = order.getOrder_detail().stream()
                .map(order_detail -> {
                    Book book =
                            bookRepository.findBookById(order_detail.getBook().getId());
                    if (book == null) {
                        throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                    }
                    Order_Detail new_order_detail = new Order_Detail();
                    int quantity = order_detail.getQuantity();
                    int price = book.getPrice()*quantity;
                    int discount =
                            book.getDiscount()*order_detail.getQuantity();
                    new_order_detail.setQuantity(quantity);
                    new_order_detail.setPrice(price);
                    new_order_detail.setDiscount(discount);
                    new_order_detail.setTotal_money(price - discount);
                    new_order_detail.setOrder(order);
                    new_order_detail.setBook(book);
                    return new_order_detail;
                }).collect(Collectors.toSet());
        order.getOrder_detail().clear();
        order.getOrder_detail().addAll(order_details);
        int total_price = Cal_Total_Price(order_details).intValue();
        order.setPrice(total_price);
        int total_discount = Cal_Total_Discount(order_details).intValue();
        order.setTotal_dis(total_discount);
        order.setTotal_price(total_price - total_discount);
        order.setStatus_trans(0);
        Payment payment = new Payment();
        log.info(order.getPayment());
        payment.setMethod_payment(order.getPayment().getMethod_payment());
        payment.setPayment_status(false);
        payment.setCustomerId(order.getCustomerId());
        payment.setOrder(order);
        order.setPayment(payment);
        return orderRepository.save(order);
    }
    @Secured("ROLE_ADMIN")
    public OrderResponse deleteOrder(int id){
        try{
            if(!orderRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Order existOrder = orderRepository.findOrderById(id);
            orderRepository.delete(existOrder);
            return orderMapper.toOrderResponse(existOrder);
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
    public OrderResponse cancelOrder(int id){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            int customer_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            if(!orderRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Order existOrder = orderRepository.findOrderById(id);
            if(existOrder.getCustomerId()!=customer_id){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            if(existOrder.getStatus_trans()==0){
               return deleteOrder(id);
            } else {
                throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
            }
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
    public OrderResponse updateOrderStatusTrans(int id, int status){
        try{
            if(!orderRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Order existOrder = orderRepository.findOrderById(id);
            if(status<= existOrder.getStatus_trans()){
                return orderMapper.toOrderResponse(existOrder);
            }
            existOrder.setStatus_trans(status);
            return orderMapper.toOrderResponse(orderRepository.save(existOrder));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }

}
