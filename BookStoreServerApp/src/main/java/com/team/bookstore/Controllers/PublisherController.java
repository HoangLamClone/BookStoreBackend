package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.LanguageRequest;
import com.team.bookstore.Dtos.Requests.PublisherRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.LanguageResponse;
import com.team.bookstore.Dtos.Responses.PublisherResponse;
import com.team.bookstore.Mappers.LanguageMapper;
import com.team.bookstore.Mappers.PublisherMapper;
import com.team.bookstore.Services.LanguageService;
import com.team.bookstore.Services.PublisherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    PublisherService publisherService;
    @Autowired
    PublisherMapper publisherMapper;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addPublisher(@RequestBody PublisherRequest publisherRequest){
        PublisherResponse result =
                publisherService.createPublisher(publisherMapper.toPublisher(publisherRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllPublishers(){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(publisherService.getAllPublisher()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findPublishersBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(publisherService.findPublisherBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updatePublisher(@PathVariable int id,
                                                         @RequestBody PublisherRequest publisherRequest){
        PublisherResponse result = publisherService.updatePublisher(id,
                publisherMapper.toPublisher(publisherRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());

    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deletePublisher(@PathVariable int id){
        PublisherResponse result = publisherService.deletePublisher(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
