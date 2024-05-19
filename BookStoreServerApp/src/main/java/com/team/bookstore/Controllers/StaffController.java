package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.CustomerInformationRequest;
import com.team.bookstore.Dtos.Requests.StaffInformationRequest;
import com.team.bookstore.Dtos.Requests.UserRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.CustomerInformationResponse;
import com.team.bookstore.Dtos.Responses.StaffInformationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Mappers.UserMapper;
import com.team.bookstore.Services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/staff")
@SecurityRequirement(name = "bearerAuth")
public class StaffController {
    @Autowired
    UserMapper   userMapper;
    @Autowired
    StaffService staffService;
    @Autowired
    UserService  userService;
    @Autowired
    ImportService importService;
    @Autowired
    ShiftService shiftService;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllStaffInformation(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(staffService.getAllStaffInformation()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findStaffInformationBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(staffService.findStaffInformationBy(keyword)).build());
    }
    @GetMapping("/myinfo")
    public ResponseEntity<APIResponse<?>> getMyInfo(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(staffService.getMyInfo()).build());
    }
    @GetMapping("/my-imports")
    public ResponseEntity<APIResponse<?>> getMyImports(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(importService.getMyImports()).build());
    }
    @GetMapping("/my-shifts")
    public ResponseEntity<APIResponse<?>> getMyShifts(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK"
        ).result(shiftService.getMyShifts()).build());
    }
    @PostMapping("/register")
    public ResponseEntity<APIResponse<?>> customerRegister(@RequestBody UserRequest userRequest){
        UserResponse result =
                staffService.staffRegister(userMapper.toUser(userRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PatchMapping("/update-password")
    public ResponseEntity<APIResponse<?>> updatePassword(@RequestBody String password){
        UserResponse result = userService.updatePassword(password);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PostMapping("/create/info/{id}")
    public ResponseEntity<APIResponse<?>> createCustomerInformation(@PathVariable int id, @RequestPart MultipartFile image, @RequestPart StaffInformationRequest staffInformationRequest){
        StaffInformationResponse result =
                staffService.createStaffInformation(id,image,
                        userMapper.toStaffInformation(staffInformationRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PatchMapping("/update/info/{id}")
    public ResponseEntity<APIResponse<?>> updateCustomerInformation(@PathVariable int id,@RequestPart MultipartFile image ,@RequestPart StaffInformationRequest staffInformationRequest){
        StaffInformationResponse result =
                staffService.updateStaffInformation(id,image,
                        userMapper.toStaffInformation(staffInformationRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
