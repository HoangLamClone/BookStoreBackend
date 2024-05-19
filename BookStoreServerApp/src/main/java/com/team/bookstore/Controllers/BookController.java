package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.BookRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.BookResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Mappers.BookMapper;
import com.team.bookstore.Services.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    BookMapper bookMapper;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createABook(@RequestPart MultipartFile sourceFile, @RequestPart BookRequest bookRequest){
        BookResponse result =
                bookService.addBook(sourceFile,bookMapper.toBook(bookRequest));
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllBook(){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(bookService.getAllBook()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateABook(@PathVariable int id,
                                                      @RequestPart MultipartFile sourceFile,
                                                      @RequestPart BookRequest updateContent){
        BookResponse result = bookService.updateABook(id,sourceFile,
                bookMapper.toBook(updateContent));
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findAllBookByKeyword(@RequestParam String keyword){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(bookService.findBooks(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteBook(@PathVariable int id){
        BookResponse result = bookService.deleteBook(id);
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
