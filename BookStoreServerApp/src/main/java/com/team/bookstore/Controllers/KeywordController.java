package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.KeywordRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.KeywordResponse;
import com.team.bookstore.Mappers.KeywordMapper;
import com.team.bookstore.Services.KeywordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keyword")
public class KeywordController {
    @Autowired
    KeywordService keywordService;
    @Autowired
    KeywordMapper keywordMapper;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllKeywords(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(keywordService.getAllKeywords())
                .build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findKeywordsBy(String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(keywordService.findKeywordsBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createAKeyword(@RequestBody KeywordRequest keywordRequest){
        KeywordResponse result =
                keywordService.createKeyword(keywordMapper.toKeyword(keywordRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateAKeyword(@PathVariable int id
            ,@RequestBody KeywordRequest keywordRequest){
        KeywordResponse result =
                keywordService.updateKeyword(id,
                        keywordMapper.toKeyword(keywordRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteAKeyword(@PathVariable int id){
        KeywordResponse result =
                keywordService.deleteKeyword(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
