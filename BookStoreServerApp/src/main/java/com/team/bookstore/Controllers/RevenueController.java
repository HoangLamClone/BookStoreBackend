package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.RevenueDayResponse;
import com.team.bookstore.Entities.RevenueDay;
import com.team.bookstore.Services.RevenueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revenue")
@SecurityRequirement(name = "bearerAuth")
public class RevenueController  {
    @Autowired
    RevenueService revenueService;
    @GetMapping("/day/all")
    public ResponseEntity<APIResponse<?>> getAllRevenueDay(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(revenueService.getAllRevenueDay()).build());
    }
    @PostMapping("/day/cal/{date}")
    public ResponseEntity<APIResponse<?>> createDateRevenue(@PathVariable String date){
        RevenueDayResponse result = revenueService.generateDayRevenue(date);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
