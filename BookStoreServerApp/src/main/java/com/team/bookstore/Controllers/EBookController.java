package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Services.Customer_BookService;
import com.team.bookstore.Services.EBookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebook")
public class EBookController {
    @Autowired
    EBookService eBookService;
    @Autowired
    Customer_BookService customerBookService;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllEBooks(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(eBookService.getAllEBook()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/free-read")
    public ResponseEntity<APIResponse<?>> readEbook(@RequestParam int book_id){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(eBookService.readEBook(book_id)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/mine")
    public ResponseEntity<APIResponse<?>> getMyPurchaseBook(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(eBookService.getMyPurchasedEBooks()).build());
    }

    @PatchMapping("/readingprocess")
    public ResponseEntity<APIResponse<?>> updateReadingProcess(@RequestParam int book_id,@RequestBody int readingprocess){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(customerBookService.updateReadingProcess(book_id,readingprocess)).build());
    }


}
