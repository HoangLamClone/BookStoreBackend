package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.PaymentResponse;
import com.team.bookstore.Entities.Order;
import com.team.bookstore.Entities.Payment;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.PaymentMapper;
import com.team.bookstore.Repositories.OrderRepository;
import com.team.bookstore.Repositories.PaymentRepository;
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

import static com.team.bookstore.Specifications.PaymentSpecification.CreatePaymentKeywordSpec;

@Service
@Log4j2
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    public List<PaymentResponse> getAllPayments(){
        try{
            return paymentRepository.findAll().stream().map(paymentMapper:: toPaymentResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<PaymentResponse> findPaymentsBy(String keyword){
        try{
            Specification<Payment> spec = CreatePaymentKeywordSpec(keyword);
            return paymentRepository.findAll(spec).stream().map(paymentMapper:: toPaymentResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<PaymentResponse> getMyPayments(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            int customer_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            return paymentRepository.findPaymentsByCustomerId(customer_id).stream().map(paymentMapper::toPaymentResponse).collect(Collectors.toList());

        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public PaymentResponse createPayment(Payment payment){
        try{
            return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public PaymentResponse verifyPayment(int id){
        try{
            if(!paymentRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Payment existPayment = paymentRepository.findPaymentById(id);
            if(existPayment.isPayment_status()){
                return paymentMapper.toPaymentResponse(paymentRepository.save(existPayment));
            }
            existPayment.setPayment_status(true);
            orderService.verifyOrder(existPayment.getOrder().getId(),1);
            return paymentMapper.toPaymentResponse(paymentRepository.save(existPayment));
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_VERIFY);
        }
    }
    @Secured("ROLE_ADMIN")
    public PaymentResponse deletePayment(int id)
    {
        try{
            if(!paymentRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Payment existPayment = paymentRepository.findPaymentById(id);
            paymentRepository.delete(existPayment);
            return paymentMapper.toPaymentResponse(existPayment);
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
