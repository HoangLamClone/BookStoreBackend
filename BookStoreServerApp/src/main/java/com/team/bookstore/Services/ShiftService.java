package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Requests.ShiftRequest;
import com.team.bookstore.Dtos.Responses.ShiftResponse;
import com.team.bookstore.Entities.Shift;
import com.team.bookstore.Entities.Staff_Shift;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.ShiftMapper;
import com.team.bookstore.Repositories.ShiftRepository;
import com.team.bookstore.Repositories.Staff_ShiftRepository;
import com.team.bookstore.Repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.ShiftSpecification.CreateShiftKeywordSpec;
import static com.team.bookstore.Specifications.Staff_ShiftSpecification.CreateStaff_ShiftStaffIdSpec;

@Service
@Log4j2
public class ShiftService {
    @Autowired
    ShiftRepository shiftRepository;
    @Autowired
    ShiftMapper shiftMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Staff_ShiftRepository staffShiftRepository;
    @Secured("ROLE_ADMIN")
    public List<ShiftResponse> getAllShifts() {
        try {
            return shiftRepository.findAll().stream().map(shiftMapper::toShiftResponse).collect(Collectors.toList());


        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }

    @Secured("ROLE_ADMIN")
    public List<ShiftResponse> findShiftBy(String keyword) {
        try {
            Specification<Shift> spec = CreateShiftKeywordSpec(keyword);
            return shiftRepository.findAll(spec).stream().map(shiftMapper::toShiftResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<ShiftResponse> getMyShifts(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null) {
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            int staff_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            Specification<Staff_Shift> spec =
                    CreateStaff_ShiftStaffIdSpec(staff_id);
            List<Shift> myShifts =
                    staffShiftRepository.findAll(spec).stream().map(staffShift -> {
                        try{
                            return shiftRepository.findShiftById(staffShift.getId().getShift_id());
                        } catch(Exception e){
                            log.info(e);
                            throw new ApplicationException(ErrorCodes.NOT_FOUND);
                        }
                    }).toList();
            return myShifts.stream().map(shiftMapper::toShiftResponse
            ).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public ShiftResponse addShift(Shift shift){
        try{
            return shiftMapper.toShiftResponse(shiftRepository.save(shift));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public ShiftResponse updateShift(int id, Shift shift){
        try{
            if(!shiftRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            shift.setId(id);
            return shiftMapper.toShiftResponse(shiftRepository.save(shift));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public ShiftResponse deleteShift(int id){
        try{
            if(!shiftRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Shift existShift = shiftRepository.findShiftById(id);
            shiftRepository.delete(existShift);
            return shiftMapper.toShiftResponse(existShift);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }

}
