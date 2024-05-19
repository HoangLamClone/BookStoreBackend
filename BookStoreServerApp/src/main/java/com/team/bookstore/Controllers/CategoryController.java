package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.CategoryRequest;
import com.team.bookstore.Dtos.Requests.ProviderRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.CategoryResponse;
import com.team.bookstore.Dtos.Responses.ProviderResponse;
import com.team.bookstore.Mappers.CategoryMapper;
import com.team.bookstore.Services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryMapper categoryMapper;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addCategory(@RequestPart MultipartFile image, @RequestPart CategoryRequest categoryRequest){
        CategoryResponse result =
                categoryService.createCategory(image,
                        categoryMapper.toCategory(categoryRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllCategories(){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(categoryService.getAllCategories()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findCategoriesBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(categoryService.findCategoriesBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateCategory(@PathVariable int id
            , @RequestPart MultipartFile image,
                                                         @RequestPart CategoryRequest categoryRequest){
        CategoryResponse result = categoryService.updateCategory(id,image,
                categoryMapper.toCategory(categoryRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());

    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteCategory(@PathVariable int id){
        CategoryResponse result = categoryService.deleteCategory(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
