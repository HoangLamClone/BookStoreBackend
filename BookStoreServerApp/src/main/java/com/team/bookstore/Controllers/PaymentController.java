package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Mappers.PaymentMapper;
import com.team.bookstore.Services.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
@Controller
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
    public String vnpayResult(HttpServletRequest request,
                              @RequestParam String vnp_TxnRef, Model model){
        paymentService.verifyPayment(vnp_TxnRef);
        model.addAttribute("vnp_TxnRef",vnp_TxnRef);
        model.addAttribute("vnp_Amount",request.getParameter("vnp_Amount"));
        model.addAttribute("vnp_OrderInfo",request.getParameter(
                "vnp_OrderInfo"));
        model.addAttribute("vnp_ResponseCode",request.getParameter(
                "vnp_ResponseCode"));
        model.addAttribute("vnp_TransactionNo",request.getParameter(
                "vnp_TransactionNo"));
        model.addAttribute("vnp_BankCode",request.getParameter("vnp_BankCode"));
        model.addAttribute("vnp_PayDate",request.getParameter("vnp_PayDate"));
        model.addAttribute("vnp_TransactionStatus",request.getParameter(
                "vnp_TransactionStatus"));
        return "vnpay_return";
    }

}