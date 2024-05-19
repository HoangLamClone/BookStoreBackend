package com.team.bookstore.Controllers;
import com.team.bookstore.Dtos.Requests.GalleryManageRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.GalleryManageResponse;
import com.team.bookstore.Mappers.GalleryManageMapper;
import com.team.bookstore.Services.GalleryManageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gallery")
public class GalleryManageController {
    @Autowired
    GalleryManageService galleryManageService;
    @Autowired
    GalleryManageMapper galleryManageMapper;
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addGallery(@RequestPart MultipartFile image,
                                                     @RequestPart GalleryManageRequest galleryManageRequest){
        GalleryManageResponse result =
                galleryManageService.addGallery(image,
                        galleryManageMapper.toGalleryManage(galleryManageRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getGallery(){
        return ResponseEntity.ok().body(APIResponse.builder().code(200).message("OK").result(galleryManageService.getAllGallery()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findGalleryBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(galleryManageService.findGalleriesBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateGallery(@PathVariable int id,
                                                        @RequestPart MultipartFile image,
                                                         @RequestPart GalleryManageRequest galleryManageRequest){
        GalleryManageResponse result =
                galleryManageService.updateGallery(id,image,
                galleryManageMapper.toGalleryManage(galleryManageRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());

    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteLanguage(@PathVariable int id){
        GalleryManageResponse result = galleryManageService.deleteGallery(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
