package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.ChapterRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.ChapterResponse;
import com.team.bookstore.Mappers.ChapterMapper;
import com.team.bookstore.Services.ChapterService;
import com.team.bookstore.Services.Customer_BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    Customer_BookService customerBookService;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllChapters(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message(
                "OK").result(chapterService.getAllChapters()).build());
    }
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addChapter(@RequestPart MultipartFile file, @RequestPart ChapterRequest chapterRequest){
        ChapterResponse ressult = chapterService.addChapter(file,
                chapterMapper.toChapter(chapterRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message(
                "OK").result(ressult).build());
    }
    @PatchMapping("/update")
    public ResponseEntity<APIResponse<?>> updateChapter(@RequestParam int book_id,
            @RequestPart MultipartFile file, @RequestPart ChapterRequest chapterRequest){
        ChapterResponse ressult = chapterService.updateChapter(book_id,file,
                chapterMapper.toChapter(chapterRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message(
                "OK").result(ressult).build());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse<?>> deleteChapter(@RequestParam int book_id){
        return ResponseEntity.ok(APIResponse.builder().code(200).message(
                "OK").result(chapterService.deleteChapter(book_id)).build());
    }
    @PatchMapping("/update-reading-chaper")
    public ResponseEntity<APIResponse<?>> updateReadingChaper(@RequestParam int book_id,@RequestParam int chapter_index){
        return ResponseEntity.ok(APIResponse.builder().code(200).message(
                "OK").result(customerBookService.updateReadingProcess(book_id
                ,chapter_index)).build());
    }
    @GetMapping("/get-reading-chaper")
    public ResponseEntity<APIResponse<?>> getReadingChapter(@RequestParam int book_id){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(customerBookService.getReadingProcess(book_id)).build());
    }
}
