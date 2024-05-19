package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.BookRequest;
import com.team.bookstore.Dtos.Requests.OrderRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Dtos.Responses.OrderResponse;
import com.team.bookstore.Mappers.OrderMapper;
import com.team.bookstore.Services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createAOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse result =
                orderService.createOrder(orderMapper.toOrder(orderRequest));
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllOrder(){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(orderService.getAllOrders()).build());
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateAOrder(@PathVariable int id,
                                                   @RequestBody OrderRequest orderRequest){
        OrderResponse result = orderService.updateOrder(id,
                orderMapper.toOrder(orderRequest));
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findAllOrderByKeyword(@RequestParam String keyword){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(orderService.findAllOrdersBy(keyword)).build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteOrder(@PathVariable int id){
        OrderResponse result = orderService.deleteOrder(id);
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<APIResponse<?>> cancelOrder(@PathVariable int id){
        OrderResponse result = orderService.cancelOrder(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<APIResponse<?>> updateOrderStatusTrans(@PathVariable int id,@RequestParam int status){
        OrderResponse result = orderService.updateOrderStatusTrans(id,status);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
