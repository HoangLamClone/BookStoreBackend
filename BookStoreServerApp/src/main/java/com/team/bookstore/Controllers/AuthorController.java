package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.AuthorRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.AuthorResponse;
import com.team.bookstore.Entities.Author;
import com.team.bookstore.Mappers.AuthorMapper;
import com.team.bookstore.Services.AuthorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @Autowired
    AuthorMapper  authorMapper;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody AuthorRequest authorRequest){
        AuthorResponse result = authorService.createAuthor(authorRequest);
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/all")
    public ResponseEntity<List<AuthorResponse>> getAllAuthor(){
        return ResponseEntity.ok().body(authorService.getAllAuthor());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findAuthorBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(authorService.findAuthorBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateAAuthor(@PathVariable int id,
                                                        @RequestBody AuthorRequest authorRequest){
        AuthorResponse result = authorService.updateAuthor(id,
                authorMapper.toAuthor(authorRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());

    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteAuthor(@PathVariable int id){
        AuthorResponse result = authorService.deleteAuthor(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }

}
