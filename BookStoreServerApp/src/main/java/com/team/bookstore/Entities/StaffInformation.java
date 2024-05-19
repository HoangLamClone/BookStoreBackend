package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "staff_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffInformation extends Auditable{
    @Id
    @Column(name = "staff_id")
    int id;
    String fullname;
    String email;
    Boolean gender;
    Date birthday;
    String phonenumber;
    String address;
    Date initiate_time;
    int salary;
    byte[] avatar;
    @JsonBackReference("staff_shift")
    @OneToMany(mappedBy = "staff_information")
    @JsonIgnore
    Set<Staff_Shift> staff_shift = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "staff_id")
    User user;
}
