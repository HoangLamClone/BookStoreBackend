package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Requests.RoleRequest;
import com.team.bookstore.Dtos.Responses.RoleResponse;
import com.team.bookstore.Entities.*;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.KeywordMapperImpl;
import com.team.bookstore.Mappers.RoleMapper;
import com.team.bookstore.Repositories.PermissionRepository;
import com.team.bookstore.Repositories.RoleRepository;
import com.team.bookstore.Repositories.Role_PermissionRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.RoleSpecification.GenerateRoleKeywordSpec;

@Service
@Log4j2
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    Role_PermissionRepository role_permissionRepository;
    @Autowired
    RoleMapper roleMapper;
    @Secured("ROLE_ADMIN")
    public List<RoleResponse> getAllRoles(){
       try{
           return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
       }
       catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_CATEGORIED);
       }
    }
    @Secured("ROLE_ADMIN")
    public List<RoleResponse> findRole(String keyword){
        try{
            Specification<Role> spec = GenerateRoleKeywordSpec(keyword);
            return roleRepository.findAll(spec).stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public RoleResponse createRole(Role role){
        try{
            if(roleRepository.existsRoleByRolename(role.getRolename())){
                throw new ApplicationException(ErrorCodes.OBJECT_HAS_BEEN_EXISTING);
            }
            Create_Role_Permission_Relation_And_Save(role);
            return roleMapper.toRoleResponse(role);
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public void Create_Role_Permission_Relation_And_Save(Role role) {
        Set<Role_Permission> role_permissions =
                role.getRole_permission().stream()
                .map(role_permission -> {
                    Permission permission =
                            permissionRepository.findPermissionByPermissionname(role_permission.getPermission().getPermissionname());
                    if (permission == null) {
                        throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                    }
                    Role_Permission new_role_permission = new Role_Permission();
                    new_role_permission.setRole(role);
                    new_role_permission.setPermission(role_permission.getPermission());
                    return new_role_permission;
                }).collect(Collectors.toSet());
        role.getRole_permission().clear();
        role.getRole_permission().addAll(role_permissions);
        roleRepository.save(role);
        roleMapper.toRoleResponse(roleRepository.findRoleByRolename(role.getRolename()));
    }
    @Secured("ROLE_ADMIN")
    public RoleResponse deleteRole(String rolename){
        try{
            if(!roleRepository.existsRoleByRolename(rolename)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Role existRole = roleRepository.findRoleByRolename(rolename);
            roleRepository.delete(existRole);
            return roleMapper.toRoleResponse(existRole);
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
    /*
    public RoleResponse createRole(RoleRequest roleRequest){
        try{
            if(roleRepository.existsRoleByRoleName(roleRequest.getRolename())){
                throw new ApplicationException(ErrorCodes.ROLE_HAVE_BEEN_EXIST);
            }
            roleRepository.save(roleMapper.toRole(roleRequest));

        }
        catch(Exception e){
            throw new ApplicationException(ErrorCodes.UN_CATEGORIED);
        }
    }

     */


}
