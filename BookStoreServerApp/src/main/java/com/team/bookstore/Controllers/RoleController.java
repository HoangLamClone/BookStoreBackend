package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Requests.RoleRequest;
import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.RoleResponse;
import com.team.bookstore.Mappers.RoleMapper;
import com.team.bookstore.Repositories.RoleRepository;
import com.team.bookstore.Services.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/role")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    RoleMapper roleMapper;
    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllRole(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(roleService.getAllRoles()).build());
    }
    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> findRoleBy(@RequestParam String keyword){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(roleService.findRole(keyword)).build());
    }
    @PostMapping("/add")
    public ResponseEntity<APIResponse<?>> addRole(@RequestBody RoleRequest roleRequest){
        RoleResponse result =
                roleService.createRole(roleMapper.toRole(roleRequest));
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());

    }
    @DeleteMapping("/delete/{rolename}")
    public ResponseEntity<APIResponse<?>> deleteRole(@PathVariable String rolename){
        RoleResponse result = roleService.deleteRole(rolename);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}

