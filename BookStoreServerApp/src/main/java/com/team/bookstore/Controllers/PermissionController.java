package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.PermissionRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.PermissionResponse;
import com.team.bookstore.Entities.Permission;
import com.team.bookstore.Mappers.PermissionMapper;
import com.team.bookstore.Services.PermissionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

@RestController
@RequestMapping("/admin/permission")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    @Autowired
    PermissionMapper permissionMapper;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllPermissison(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(permissionService.getAllPermission()).build());
    }
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> createAPermisison(@RequestBody  PermissionRequest permissionRequest){
        PermissionResponse result =
                permissionService.createPermission(permissionMapper.toPermission(permissionRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findAllPermissionBy(@RequestParam String keyword){
        List<PermissionResponse> result =
                permissionService.findPermission(keyword);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @DeleteMapping("/delete/{permissionname}")
    public ResponseEntity<APIResponse<?>> deletePermission(@PathVariable String permissionname){
        PermissionResponse result =
                permissionService.deletePermission(permissionname);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
