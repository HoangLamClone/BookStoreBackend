package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.AuthorRequest;
import com.team.bookstore.Dtos.Requests.LanguageRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.AuthorResponse;
import com.team.bookstore.Dtos.Responses.LanguageResponse;
import com.team.bookstore.Entities.Language;
import com.team.bookstore.Mappers.AuthorMapper;
import com.team.bookstore.Mappers.LanguageMapper;
import com.team.bookstore.Services.AuthorService;
import com.team.bookstore.Services.LanguageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/language")
public class LanguageController {
    @Autowired
    LanguageService languageService;
    @Autowired
    LanguageMapper languageMapper;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addLanguage(@RequestBody LanguageRequest languageRequest){
        LanguageResponse result =
                languageService.createLanguage(languageMapper.toLanguage(languageRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllLanguage(){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(languageService.getAllLanguage()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findLanguageBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(languageService.findLanguagesBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateLanguage(@PathVariable int id,
                                                        @RequestBody LanguageRequest languageRequest){
        LanguageResponse result = languageService.updateLanguage(id,
                languageMapper.toLanguage(languageRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());

    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteLanguage(@PathVariable int id){
        LanguageResponse result = languageService.deleteLanguage(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
