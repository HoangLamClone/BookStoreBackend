package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.RevenueDay;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

@Log4j2
public class RevenueSpecification {
    public static Specification<RevenueDay> CreateRevenueDaySpec(String year_month){
        return new Specification<RevenueDay>() {
            @Override
            public Predicate toPredicate(Root<RevenueDay> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(year_month.length()!=7){
                    return criteriaBuilder.conjunction();
                }
                SimpleDateFormat sdf       = new SimpleDateFormat("yyyy-MM");
                Timestamp        startTime = null;
                Date MonthYear = null;
                try {
                    MonthYear = sdf.parse(year_month);
                    startTime = new Timestamp(sdf.parse(year_month).getTime());
                } catch (Exception e) {
                    log.info(e);
                }
                Month month = null;
                Calendar calendar = Calendar.getInstance();
                assert MonthYear != null;
                calendar.setTime(MonthYear);
                    switch (calendar.get(Calendar.MONTH)){
                        case 1:
                            month = Month.JANUARY;
                            break;
                        case 2:
                            month = Month.FEBRUARY;
                            break;
                        case 3:
                            month = Month.MARCH;
                            break;
                        case 4:
                            month = Month.APRIL;
                            break;
                        case 5:
                            month = Month.MAY;
                            break;
                        case 6:
                            month = Month.JUNE;
                            break;
                        case 7:
                            month = Month.JULY;
                            break;
                        case 8:
                            month = Month.AUGUST;
                            break;
                        case 9:
                            month = Month.SEPTEMBER;
                            break;
                        case 10:
                            month = Month.OCTOBER;
                            break;
                        case 11:
                            month = Month.NOVEMBER;
                            break;
                        case 12:
                            month = Month.DECEMBER;
                            break;
                    }

                assert month != null;
                long days = month.length(calendar.get(Calendar.YEAR) % 4 == 0 && (calendar.get(Calendar.YEAR) % 100 != 0 || calendar.get(Calendar.YEAR) % 400 == 0));
                assert startTime != null;
                Timestamp endTime =
                        new Timestamp(startTime.getTime() + days*24*60*60*1000);

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(
                                "createAt"),startTime),
                        criteriaBuilder.lessThan(root.get("createAt"),endTime)
                );
            }
        };
    }

}
