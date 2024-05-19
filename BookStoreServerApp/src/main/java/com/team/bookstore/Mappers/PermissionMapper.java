package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.PermissionRequest;
import com.team.bookstore.Dtos.Responses.PermissionResponse;
import com.team.bookstore.Entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "role_permission",ignore = true)
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
