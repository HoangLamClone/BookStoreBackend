package com.team.bookstore.Services;
import com.team.bookstore.Dtos.Responses.AuthenticationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.UserMapper;
import com.team.bookstore.Repositories.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.team.bookstore.Specifications.UserSpecifications.GenerateUserKeywordSpec;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Secured("ROLE_ADMIN")
    public List<UserResponse> getAllUser(){
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            log.info("authentication: " + authentication.getName());
            return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
        }catch(Exception e){

            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public UserResponse updatePassword(String password){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            int user_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            if(!userRepository.existsById(user_id)){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            User existUser =
                    userRepository.findUserById(user_id);
            existUser.setPassword(passwordEncoder.encode(password));
            return userMapper.toUserResponse(userRepository.save(existUser));
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<UserResponse> findUser(String keyword){
        try{
            Specification<User> spec =
                    GenerateUserKeywordSpec(keyword);
            return userRepository.findAll(spec).stream().map(userMapper::toUserResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
        }
    }
    @Secured("ROLE_ADMIN")
    public UserResponse deleteUser(int id){
        try{
            User existUser = userRepository.findUserById(id);
            if(existUser == null){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            userRepository.delete(existUser);
            return userMapper.toUserResponse(existUser);
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
