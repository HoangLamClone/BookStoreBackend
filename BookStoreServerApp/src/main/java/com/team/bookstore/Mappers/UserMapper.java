package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.*;
import com.team.bookstore.Dtos.Responses.CustomerInformationResponse;
import com.team.bookstore.Dtos.Responses.StaffInformationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Entities.*;
import jdk.jshell.spi.ExecutionControl;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "user_role",ignore = true)
    @Mapping(target = "token",ignore = true)
    @Mapping(target = "customer_information",ignore = true)
    @Mapping(target = "staff_information",ignore = true)
    @Mapping(target = "send_messages",ignore = true)
    @Mapping(target = "receive_messages",ignore = true)
    User toUser(UsernameLoginRequest usernameLoginRequest);
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "token",ignore = true)
    @Mapping(target = "user_role",ignore = true)
    @Mapping(target = "customer_information",ignore = true)
    @Mapping(target = "staff_information",ignore = true)
    @Mapping(target = "send_messages",ignore = true)
    @Mapping(target = "receive_messages",ignore = true)
    User toUser(UserRequest userRequest);
    @Mapping(target = "roles",source = "user_role",qualifiedByName = "toRole")
    UserResponse toUserResponse(User user);
    @Named("toRole")
    default Set<Role> toRole(Set<User_Role> user_role){
        Set<Role> roles = new HashSet<>();
        user_role.forEach(userRole -> {
            roles.add(userRole.getRole());
        });
        return roles;
    }
    @Mapping(target = "avatar",ignore = true)
    CustomerInformation toCustomerInformation(CustomerInformation customerInformation);
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "customer_book",ignore = true)
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "feedback",ignore = true)
    @Mapping(target = "avatar",ignore = true)
    @Mapping(target = "isvip",ignore = true)
    CustomerInformation toCustomerInformation(CustomerInformationRequest customerInformationRequest);
    CustomerInformationResponse toCustomerInformationResponse(CustomerInformation customerInformation);
    @Mapping(target = "avatar",ignore = true)
    StaffInformation toStaffInformation(StaffInformation staffInformation);
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "staff_shift",ignore = true)
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "avatar",ignore = true)
    StaffInformation toStaffInformation(StaffInformationRequest staffInformationRequest);
    StaffInformationResponse toStaffInformationResponse(StaffInformation staffInformation);

}
