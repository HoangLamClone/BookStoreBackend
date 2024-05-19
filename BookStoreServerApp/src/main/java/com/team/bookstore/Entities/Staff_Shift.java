package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.bookstore.Entities.ComposeKey.StaffShiftKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "staff_shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff_Shift {
    @EmbeddedId
    StaffShiftKey id = new StaffShiftKey();
    Boolean hasWorkThisShift;
    @JsonBackReference("shift")
    @ManyToOne
    @MapsId("shift_id")
    @JoinColumn(name = "shift_id")
    Shift shift;
    @JsonManagedReference("staff_shift")
    @ManyToOne
    @MapsId("staff_id")
    @JoinColumn(name = "staff_id")
    StaffInformation staff_information;
}
