package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.bookstore.Entities.ComposeKey.ImportDetailKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.util.Date;

@Entity
@Table(name = "import_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Import_Detail {
    @EmbeddedId
    ImportDetailKey id = new ImportDetailKey();
    int quantity;
    int import_cost;
    int total_import_cost;
    @JsonBackReference("book")
    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    Book book;
    @JsonBackReference("import")
    @ManyToOne
    @MapsId("import_id")
    @JoinColumn(name = "import_id")
    @JsonIgnore
    Import ymport;
}
