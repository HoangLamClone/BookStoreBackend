package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.MessageRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.MessageResponse;
import com.team.bookstore.Mappers.MessageMapper;
import com.team.bookstore.Services.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    MessageMapper  messageMapper;
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllMessages()
    {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(messageService.getAllMessages()).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> getFindMessages(@RequestParam String keyword)
    {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(messageService.findMessagesBy(keyword)).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createAMessage(@RequestBody MessageRequest messageRequest){
        MessageResponse result =
                messageService.createMessage(messageMapper.toMessage(messageRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteAMessage(@PathVariable int id){
        MessageResponse result =
                messageService.deleteMessage(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/loadchat")
    public ResponseEntity<APIResponse<?>> loadMyChats(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(messageService.loadMyChat()).build());
    }
}
