package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.RevenueDayResponse;
import com.team.bookstore.Entities.*;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.RevenueMapper;
import com.team.bookstore.Repositories.ImportRepository;
import com.team.bookstore.Repositories.OrderRepository;
import com.team.bookstore.Repositories.RevenueDayRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.ImportSpecification.CreateImportDateSpec;
import static com.team.bookstore.Specifications.OrderSpecification.CreateOrderDateSpec;

@Service
@Log4j2
public class RevenueService {
    @Autowired
    RevenueMapper revenueMapper;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ImportRepository     importRepository;
    @Autowired
    RevenueDayRepository revenueDayRepository;
    /*
    public List<RevenueYear> getAllRevenueYear(){
        try{
            return revenueYearRepository.findAll();
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
/*
    public List<RevenueMonth> getAllRevenueMonth(){
        try{
            return revenueMonthRepository.findAll();
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }


 */
    @Secured("ROLE_ADMIN")
    public List<RevenueDayResponse> getAllRevenueDay(){
        try{
            return revenueDayRepository.findAll().stream().map(revenueMapper::toRevenueDayResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public RevenueDayResponse generateDayRevenue(String date){
        try{
            Specification<Order>  orderSpec  = CreateOrderDateSpec(date);
            Specification<Import> importSpec = CreateImportDateSpec(date);
            List<Order> orders = orderRepository.findAll(orderSpec);
            List<Import>  imports     = importRepository.findAll(importSpec);
            AtomicInteger totalSales  = new AtomicInteger();
            AtomicInteger totalImport = new AtomicInteger();
            orders.forEach(order -> {
                totalSales.addAndGet(order.getTotal_price());
            });
            imports.forEach(_import->{
                totalImport.addAndGet(_import.getImport_total());
            });
            RevenueDay revenueDay = new RevenueDay();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            revenueDay.setDay(sdf.parse(date));
            revenueDay.setTotal_sale(totalSales.longValue());
            revenueDay.setTotal_import(totalImport.longValue());
            revenueDay.setRevenue(totalSales.longValue() - totalImport.longValue());
            return revenueMapper.toRevenueDayResponse(revenueDayRepository.save(revenueDay));
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_CATEGORIED);
        }
    }
}
