package com.team.bookstore.Services;

import com.team.bookstore.Configs.VNPAYConfig;
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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.team.bookstore.Configs.VNPAYConfig.*;
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
    public PaymentResponse verifyPayment(String vnp_TxnRef){
        try{
            Payment existPayment =
                    paymentRepository.findPaymentByVnpaycode(Integer.parseInt(vnp_TxnRef));
            if(existPayment==null){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
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
    public PaymentResponse payForOrder(int order_id, short method){
        try{
            if(method<1 || method >3){
                throw new ApplicationException(ErrorCodes.INVALID_OBJECT);
            }
            if(!orderRepository.existsById(order_id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Order order = orderRepository.findOrderById(order_id);
            int payment_id =order.getId();
            String vnp_Version = VNPAYConfig.vnp_Version;
            String vnp_Command = VNPAYConfig.vnp_Command;
            String orderType = VNPAYConfig.orderType;
            long amount     = order.getTotal_price()* 100L;
            String bankCode = switch (method) {
                case 1 -> "VNPAYQR";
                case 2 -> "VNBANK";
                case 3 -> "INTCARD";
                default -> null;
            };

            String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
            String vnp_IpAddr = "192.0.0.1";

            String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo",
                    "Thanh toan cho don hang: " + String.valueOf(order.getId()));
            vnp_Params.put("vnp_OrderType", orderType);
            String locate = "vn";
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
            }
            vnp_Params.put("vnp_ReturnUrl", VNPAYConfig.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            cld.add(Calendar.HOUR,7);
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash =
                    VNPAYConfig.hmacSHA512(VNPAYConfig.secretKey,
                            hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;
            Payment payment =
                    paymentRepository.findPaymentById(order.getPayment().getId());
            payment.setVnpaycode(Integer.valueOf(vnp_TxnRef));
            payment.setMethod_payment(method);
            paymentRepository.save(payment);
            if(payment.isPayment_status()){
                throw new ApplicationException(ErrorCodes.PURCHASED);
            }
            PaymentResponse paymentResponse =
                    paymentMapper.toPaymentResponse(payment);
            paymentResponse.setPaymentURL(paymentUrl);
            return paymentResponse;
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_VERIFY);
        }
    }
}
