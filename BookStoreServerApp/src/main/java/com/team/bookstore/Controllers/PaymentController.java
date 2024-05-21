package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.PaymentResponse;
import com.team.bookstore.Entities.Payment;
import com.team.bookstore.Mappers.PaymentMapper;
import com.team.bookstore.Services.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
    public ResponseEntity<APIResponse<?>> verifyPayment(@RequestParam String vnp_txnRef) {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.verifyPayment(vnp_txnRef)).build());
    }
    @PostMapping("/payfor")
    public ResponseEntity<APIResponse<?>> payFor(@RequestParam int order_id,
                                                 @RequestParam short method){
        return ResponseEntity.ok(APIResponse.builder().message("OK").code(200).result(paymentService.payForOrder(order_id,method)).build());
    }
    @GetMapping("/vnpay-result")
    public ModelAndView vnpayResult(HttpServletRequest request,
                                    HttpServletResponse response, @RequestParam String vnp_TxnRef){
        paymentService.verifyPayment(vnp_TxnRef);
        return new ModelAndView("vnpay_return");
    }

}