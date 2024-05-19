package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.ProviderRequest;
import com.team.bookstore.Dtos.Requests.PublisherRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.ProviderResponse;
import com.team.bookstore.Dtos.Responses.PublisherResponse;
import com.team.bookstore.Mappers.ProviderMapper;
import com.team.bookstore.Services.ProviderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    ProviderService providerService;
    @Autowired
    ProviderMapper providerMapper;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addProvider(@RequestBody ProviderRequest providerRequest){
        ProviderResponse result =
                providerService.createProvider(providerMapper.toProvider(providerRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllProviders(){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(providerService.getAllProvider()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findProvidersBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(providerService.findProvidersBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateProvider(@PathVariable int id,
                                                          @RequestBody ProviderRequest providerRequest){
        ProviderResponse result = providerService.updateProvider(id,
                providerMapper.toProvider(providerRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());

    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteProvider(@PathVariable int id){
        ProviderResponse result = providerService.deleteProvider(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
