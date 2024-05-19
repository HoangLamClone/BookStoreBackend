package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.ImportRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.ImportResponse;
import com.team.bookstore.Mappers.ImportMapper;
import com.team.bookstore.Services.ImportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/import")
@SecurityRequirement(name = "bearerAuth")
public class ImportController {
    @Autowired
    ImportService importService;
    @Autowired
    ImportMapper importMapper;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllImports(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(importService.getAllImports()).build());
    }

    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findImportsBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(importService.findImports(keyword)).build());
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createAnImport(@RequestBody ImportRequest importRequest){
        ImportResponse result =
                importService.createImport(importMapper.toImport(importRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }

    @PatchMapping("/verify/{id}")
    public ResponseEntity<APIResponse<?>> verifyAnImport(@PathVariable int id){
        ImportResponse result = importService.verifyImport(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateAnImport(@PathVariable int id
            ,@RequestBody ImportRequest importRequest){
        ImportResponse result = importService.updateImport(id,
                importMapper.toImport(importRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteAnImport(@PathVariable int id){
        ImportResponse result = importService.deleteImport(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
