package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.ShiftResponse;
import com.team.bookstore.Dtos.Responses.Staff_ShiftResponse;
import com.team.bookstore.Entities.ComposeKey.StaffShiftKey;
import com.team.bookstore.Entities.Staff_Shift;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.Staff_ShiftMapper;
import com.team.bookstore.Repositories.ShiftRepository;
import com.team.bookstore.Repositories.StaffInformationRepository;
import com.team.bookstore.Repositories.Staff_ShiftRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.Staff_ShiftSpecification.CreateStaff_ShiftKeywordSpec;

@Service
@Log4j2
public class Staff_ShiftService {
    @Autowired
    Staff_ShiftRepository staffShiftRepository;
    @Autowired
    Staff_ShiftMapper staffShiftMapper;
    @Autowired
    StaffInformationRepository staffInformationRepository;
    @Autowired
    ShiftRepository shiftRepository;
    @Secured("ROLE_ADMIN")
    public List<Staff_ShiftResponse> getAllStaffShifts(){
        try{
            return staffShiftRepository.findAll().stream().map(staffShiftMapper::toStaff_ShiftResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<Staff_ShiftResponse> findAllStaffShiftsBy(String keyword){
        try{
            Specification<Staff_Shift> spec =
                    CreateStaff_ShiftKeywordSpec(keyword);
            return staffShiftRepository.findAll(spec).stream().map(staffShiftMapper::toStaff_ShiftResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public Staff_ShiftResponse createStaff_Shift(Staff_Shift staffShift){
        try{
            int staff_id = staffShift.getStaff_information().getId();
            int shift_id = staffShift.getShift().getId();
            if(!staffInformationRepository.existsById(staff_id) || !shiftRepository.existsById(shift_id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);

            }
            staffShift.setHasWorkThisShift(false);
            return staffShiftMapper.toStaff_ShiftResponse(staffShiftRepository.save(staffShift));
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public Staff_ShiftResponse updateStaff_Shift(StaffShiftKey id,
                                                 Staff_Shift staffShift){
        try{
            if(!staffShiftRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            staffShift.setId(id);
            return staffShiftMapper.toStaff_ShiftResponse(staffShiftRepository.save(staffShift));
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public Staff_ShiftResponse deleteStaff_Shift(StaffShiftKey id){
        try{
            if(!staffShiftRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Staff_Shift existStaff_Shift =
                    staffShiftRepository.findStaff_ShiftById(id);
            staffShiftRepository.delete(existStaff_Shift);
            return staffShiftMapper.toStaff_ShiftResponse(existStaff_Shift);
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
}
