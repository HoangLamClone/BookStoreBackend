package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shift extends Auditable{
    @Id
    @Column(name = "shift_id")
    @GeneratedValue(strategy =  GenerationType.AUTO)
    Integer id;
    Date start_time;
    Date end_time;
    String description;
    @JsonManagedReference("shift")
    @OneToMany(mappedBy = "shift",cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Staff_Shift> staff_shift = new HashSet<>();
}
