package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "imports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Import extends Auditable{
    @Id
    @Column(name = "import_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    int id;
    int import_total;
    boolean import_status;
    @JsonManagedReference("import")
    @OneToMany(mappedBy = "ymport" ,cascade =  CascadeType.ALL,fetch =
            FetchType.EAGER)
    Set<Import_Detail> import_detail = new HashSet<>();
}
