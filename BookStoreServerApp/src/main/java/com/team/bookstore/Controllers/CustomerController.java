package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.CustomerInformationRequest;
import com.team.bookstore.Dtos.Requests.CustomerRegisterRequest;
import com.team.bookstore.Dtos.Requests.UserRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.CustomerInformationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.UserMapper;
import com.team.bookstore.Repositories.CustomerInformationRepository;
import com.team.bookstore.Repositories.UserRepository;
import com.team.bookstore.Services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Email;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Objects;


@Log4j2
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;
    @Autowired
    BookService bookService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerInformationRepository customerInformationRepository;
    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")

    public ResponseEntity<APIResponse<?>> getAllCustomerInformation(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(customerService.getAllCustomerInformation()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findCustomerInformationBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(customerService.findCustomerInformationBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/myinfo")
    public ResponseEntity<APIResponse<?>> getMyInfo(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(customerService.getMyInfo()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my-payments")
    public ResponseEntity<APIResponse<?>> getMyPayments(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.getMyPayments()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my-orders")
    public ResponseEntity<APIResponse<?>> getMyOrders(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(orderService.getMyOrder()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/mine")
    public ResponseEntity<APIResponse<?>> getMyBooks(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(bookService.getMyBooks()).build());
    }
    @PostMapping("/register")
    public ResponseEntity<APIResponse<?>> customerRegister(@RequestBody UserRequest userRequest){
        UserResponse result =
                customerService.customerRegister(userMapper.toUser(userRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update-password")
    public ResponseEntity<APIResponse<?>> updatePassword(@RequestBody String password){
        UserResponse result = userService.updatePassword(password);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/create/info/{id}")
    public ResponseEntity<APIResponse<?>> createCustomerInformation(@PathVariable int id, @RequestPart MultipartFile image, @RequestPart CustomerInformationRequest customerInformationRequest){
        CustomerInformationResponse result =
                customerService.createCustomerInformation(id,image,
                        userMapper.toCustomerInformation(customerInformationRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/info/{id}")
    public ResponseEntity<APIResponse<?>> updateCustomerInformation(@PathVariable int id,@RequestPart MultipartFile image, @RequestPart CustomerInformationRequest customerInformationRequest){
        CustomerInformationResponse result =
                customerService.updateCustomerInformation(id,image,
                        userMapper.toCustomerInformation(customerInformationRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PostMapping("/mobile-register")
    public ResponseEntity<APIResponse<?>> customerRegister(@RequestBody CustomerRegisterRequest customerRegisterRequest){
        if(!Objects.equals(customerRegisterRequest.getPassword(), customerRegisterRequest.getRepassword())){{
            throw new ApplicationException(ErrorCodes.INVALID_OBJECT);
        }}
        User user = new User();
        user.setUsername(customerRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(customerRegisterRequest.getPassword()));
        int user_id = userRepository.save(user).getId();
        CustomerInformation customerInformation = new CustomerInformation();
        customerInformation.setId(user_id);
        customerInformation.setFullname(customerRegisterRequest.getFullname());
        customerInformation.setEmail(customerRegisterRequest.getEmail());
        customerInformation.setPhonenumber(customerRegisterRequest.getPhonenumber());
        CustomerInformationResponse result = userMapper.toCustomerInformationResponse(customerInformationRepository.save(customerInformation));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
