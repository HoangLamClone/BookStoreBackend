package com.team.bookstore.Services;
import com.team.bookstore.Dtos.Responses.CustomerInformationResponse;
import com.team.bookstore.Dtos.Responses.StaffInformationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Entities.StaffInformation;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Entities.User_Role;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.UserMapper;
import com.team.bookstore.Repositories.RoleRepository;
import com.team.bookstore.Repositories.StaffInformationRepository;
import com.team.bookstore.Repositories.UserRepository;
import com.team.bookstore.Repositories.User_RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.UserSpecifications.GenerateStaffKeywordSpec;

@Service
@Log4j2
public class StaffService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    User_RoleRepository user_roleRepository;
    @Autowired
    StaffInformationRepository staffInformationRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Secured("ROLE_ADMIN")
    public UserResponse staffRegister(User user){
        try {
            if(userRepository.existsByUsername(user.getUsername())){
                throw new ApplicationException(ErrorCodes.USER_HAS_BEEN_EXIST);
            }
            String decodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(decodePassword);
            User_Role user_role = new User_Role();
            user_role.setUser(user);
            user_role.setRole(roleRepository.findRoleByRolename("STAFF"));
            user.getUser_role().add(user_role);
            return userMapper.toUserResponse(userRepository.save(user));
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.REGISTER_DENIED);
        }
    }
    @Secured("ROLE_ADMIN")
    public StaffInformationResponse createStaffInformation(int id,
                                                           MultipartFile image,
                                                                 StaffInformation staffInformation){
        try{
            staffInformation.setAvatar(image.getBytes());
            if(!userRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            staffInformation.setId(id);
            StaffInformation savedStaffInformation = staffInformationRepository.save(staffInformation);
            return userMapper.toStaffInformationResponse(savedStaffInformation);

        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.REGISTER_DENIED);
        }
    }
    public StaffInformationResponse getMyInfo(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.NOT_FOUND);
            }
            if(!userRepository.existsByUsername(authentication.getName())){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            int staff_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            return userMapper.toStaffInformationResponse(staffInformationRepository.findStaffInformationById(staff_id));
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public StaffInformationResponse updateStaffInformation(int id,
                                                           MultipartFile image,
                                                                 StaffInformation staffInformation){
        try{
            staffInformation.setAvatar(image.getBytes());
            if(!userRepository.existsById(staffInformation.getId()) && staffInformationRepository.existsStaffInformationById(staffInformation.getId())){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            staffInformation.setId(id);
            StaffInformation savedStaffInformation = staffInformationRepository.save(staffInformation);
            return userMapper.toStaffInformationResponse(savedStaffInformation);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<StaffInformationResponse> getAllStaffInformation(){
        try {
            return staffInformationRepository.findAll().stream().map(userMapper::toStaffInformationResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<StaffInformationResponse> findStaffInformationBy(String keyword){
        try{
            Specification<StaffInformation> spec =
                    GenerateStaffKeywordSpec(keyword);
            return staffInformationRepository.findAll(spec).stream().map(userMapper::toStaffInformationResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
        }
    }
}