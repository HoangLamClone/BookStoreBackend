package com.team.bookstore.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "revenue_day")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevenueDay extends Auditable{
    @Id
    Date day;
    long total_sale;
    long total_import;
    long revenue;
}
