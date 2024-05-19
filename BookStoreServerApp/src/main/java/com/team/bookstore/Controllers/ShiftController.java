package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.ShiftRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.ShiftResponse;
import com.team.bookstore.Mappers.ShiftMapper;
import com.team.bookstore.Services.ShiftService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shift")
@SecurityRequirement(name = "bearerAuth")
public class ShiftController {
    @Autowired
    ShiftService shiftService;
    @Autowired
    ShiftMapper shiftMapper;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllShifts(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(shiftService.getAllShifts()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findShiftsBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(shiftService.findShiftBy(keyword)).build());
    }
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createAShift(@RequestBody ShiftRequest shiftRequest){
        ShiftResponse result =
                shiftService.addShift(shiftMapper.toShift(shiftRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<?>> updateAShift(@PathVariable int id,
                                                       @RequestBody ShiftRequest shiftRequest){
        ShiftResponse result =
                shiftService.updateShift(id,shiftMapper.toShift(shiftRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<?>> deleteShift(@PathVariable int id){
        ShiftResponse result = shiftService.deleteShift(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
