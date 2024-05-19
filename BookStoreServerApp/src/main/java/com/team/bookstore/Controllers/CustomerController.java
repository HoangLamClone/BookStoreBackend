package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.CustomerInformationRequest;
import com.team.bookstore.Dtos.Requests.UserRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.CustomerInformationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Mappers.UserMapper;
import com.team.bookstore.Services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

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
}
