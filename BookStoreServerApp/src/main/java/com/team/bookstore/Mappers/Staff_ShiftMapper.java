package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.Staff_ShiftRequest;
import com.team.bookstore.Dtos.Responses.Staff_ShiftResponse;
import com.team.bookstore.Entities.Shift;
import com.team.bookstore.Entities.StaffInformation;
import com.team.bookstore.Entities.Staff_Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface Staff_ShiftMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "staff_information",source = "staff_id",qualifiedByName =
            "toStaff")
    @Mapping(target = "shift",source = "shift_id",qualifiedByName = "toShift")
    Staff_Shift toStaff_Shift(Staff_ShiftRequest staffShiftRequest);
    @Named("toStaff")
    default StaffInformation toStaff(int staff_id){
        StaffInformation staff = new StaffInformation();
        staff.setId(staff_id);
        return staff;
    }
    @Named("toShift")
    default Shift toShift(int shift_id){
        Shift shift = new Shift();
        shift.setId(shift_id);
        return shift;
    }
    @Mapping(target = "staff_id",source = "staff_information",qualifiedByName =
            "toStaff_id")
    @Mapping(target = "fullname",source = "staff_information",
            qualifiedByName = "toStaffFullname")
    Staff_ShiftResponse toStaff_ShiftResponse(Staff_Shift staffShift);
    @Named("toStaff_id")
    default int toStaff_id(StaffInformation staffInformation){
        return staffInformation.getId();
    }
    @Named("toStaffFullname")
    default String toStaffFullName(StaffInformation staffInformation){
        return staffInformation.getFullname();
    }
}
