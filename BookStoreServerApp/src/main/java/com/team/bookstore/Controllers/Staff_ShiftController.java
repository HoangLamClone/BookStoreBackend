package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.Staff_ShiftRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.Staff_ShiftResponse;
import com.team.bookstore.Entities.ComposeKey.StaffShiftKey;
import com.team.bookstore.Entities.Staff_Shift;
import com.team.bookstore.Mappers.Staff_ShiftMapper;
import com.team.bookstore.Repositories.Staff_ShiftRepository;
import com.team.bookstore.Services.Staff_ShiftService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@SecurityRequirement(name = "bearerAuth")
public class Staff_ShiftController {
    @Autowired
    Staff_ShiftService staffShiftService;
    @Autowired
    Staff_ShiftMapper staffShiftMapper;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllSchedules()
    {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(staffShiftService.getAllStaffShifts()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findSchedulesBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(staffShiftService.findAllStaffShiftsBy(keyword)).build());
    }
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addASchedule(@RequestBody Staff_ShiftRequest staffShiftRequest) {
        Staff_ShiftResponse result =
                staffShiftService.createStaff_Shift(staffShiftMapper.toStaff_Shift(staffShiftRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PatchMapping("/update/{staff_id}&{shift_id}")
    public ResponseEntity<APIResponse<?>> updateASchedule(@PathVariable int staff_id,@PathVariable int shift_id ,@RequestBody Staff_ShiftRequest staffShiftRequest){
        StaffShiftKey id = new StaffShiftKey(staff_id,shift_id);
        Staff_ShiftResponse result = staffShiftService.updateStaff_Shift(id,
                staffShiftMapper.toStaff_Shift(staffShiftRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @DeleteMapping("/delete/{staff_id}&{shift_id}")
    public ResponseEntity<APIResponse<?>> deleteASchedule(@PathVariable int staff_id,
                                            @PathVariable int shift_id){
        StaffShiftKey id = new StaffShiftKey(staff_id,shift_id);
        Staff_ShiftResponse result = staffShiftService.deleteStaff_Shift(id);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
 }

