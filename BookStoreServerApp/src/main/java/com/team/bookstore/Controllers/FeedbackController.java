package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.FeedBackRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.FeedbackResponse;
import com.team.bookstore.Mappers.FeedbackMapper;
import com.team.bookstore.Services.FeedbackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    FeedbackMapper feedbackMapper;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllFeedbacks(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(feedbackService.getAllFeedbacks()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findFeedbacksBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(feedbackService.findFeedBacksBy(keyword)).build());
    }
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createAFeedback(@RequestBody FeedBackRequest feedBackRequest){
        FeedbackResponse result =
                feedbackService.createFeedback(feedbackMapper.toFeedback(feedBackRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteAFeedback(@PathVariable int id){
        FeedbackResponse result =
                feedbackService.deleteFeedback(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
