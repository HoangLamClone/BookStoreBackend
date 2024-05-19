package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Entities.Payment;
import com.team.bookstore.Mappers.PaymentMapper;
import com.team.bookstore.Services.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentMapper  paymentMapper;

    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllPayments() {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.getAllPayments()).build());
    }

    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> getAllPayments(@RequestParam String keyword) {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.findPaymentsBy(keyword)).build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/verify")
    public ResponseEntity<APIResponse<?>> verifyPayment(@RequestParam int id) {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.verifyPayment(id)).build());
    }
    @PostMapping("/payfor")
    public ResponseEntity<APIResponse<?>> payFor(@RequestParam int order_id, HttpServletRequest request){
        return ResponseEntity.ok(APIResponse.builder().message("OK").code(200).result(paymentService.payForOrder(order_id,getClientIpAddress(request))).build());
    }
    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}