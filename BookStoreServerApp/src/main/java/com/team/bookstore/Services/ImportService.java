package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Requests.ImportRequest;
import com.team.bookstore.Dtos.Responses.ImportResponse;
import com.team.bookstore.Entities.*;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.ImportMapper;
import com.team.bookstore.Repositories.BookRepository;
import com.team.bookstore.Repositories.ImportRepository;
import com.team.bookstore.Repositories.Import_DetailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.ImportSpecification.CreateImportKeywordSpec;

@Service
@Log4j2
public class ImportService {
    @Autowired
    ImportRepository importRepository;
    @Autowired
    ImportMapper importMapper;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    Import_DetailRepository importDetailRepository;
    @Secured("ROLE_ADMIN")
    public List<ImportResponse> getAllImports(){
        try{
            return importRepository.findAll().stream().map(importMapper::toImportResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<ImportResponse> findImports(String keyword){
        try{
            Specification<Import> spec =  CreateImportKeywordSpec(keyword);
            return importRepository.findAll(spec).stream().map(importMapper::toImportResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }

    public List<ImportResponse> getMyImports(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UN_AUTHENTICATED);
            }
            return importRepository.findImportsByCreateBy(authentication.getName()).stream().map(importMapper::toImportResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public ImportResponse createImport(Import _import){
        try{
            Import savedImport =
                    Create_Import_Detail_Relation_And_Save(_import,false);
            return importMapper.toImportResponse(savedImport);
        }catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public ImportResponse updateImport(int id,Import _import){
        try{
            if(!importRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Import existImport = importRepository.findImportById(id);
            if(existImport.isImport_status()){
                throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
            }
            _import.setId(id);
            BeanUtils.copyProperties(existImport,_import);
            Import updatedImport =
                    Create_Import_Detail_Relation_And_Save(_import,true);
            return importMapper.toImportResponse(updatedImport);
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    public ImportResponse deleteImport(int id){
        try{
            if(!importRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Import existImport = importRepository.findImportById(id);
            importRepository.delete(existImport);
            return importMapper.toImportResponse(existImport);
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    AtomicInteger Cal_Total_Import(Set<Import_Detail> import_details){
        AtomicInteger total_Import_Cost_All = new AtomicInteger();
        import_details.forEach(import_detail -> {
            total_Import_Cost_All.addAndGet(import_detail.getTotal_import_cost());
        });
        return total_Import_Cost_All;
    }
    public Import Create_Import_Detail_Relation_And_Save(Import _import,
                                                         boolean isUpdate) {
        Set<Import_Detail> import_details = _import.getImport_detail().stream()
                .map(import_detail -> {
                    Book book =
                            bookRepository.findBookById(import_detail.getBook().getId());
                    if (book == null) {
                        throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                    }
                    Import_Detail new_import_detail = new Import_Detail();
                    new_import_detail.setBook(book);
                    new_import_detail.setYmport(_import);
                    int quantity = import_detail.getQuantity();
                    int import_cost = import_detail.getImport_cost();
                    new_import_detail.setQuantity(quantity);
                    new_import_detail.setImport_cost(import_cost);
                    new_import_detail.setTotal_import_cost(quantity * import_cost);
                    return new_import_detail;
                }).collect(Collectors.toSet());
        if(!isUpdate) {
            _import.getImport_detail().clear();
            _import.getImport_detail().addAll(import_details);
        } else{
            List<Import_Detail> existImportDetails =
                    importDetailRepository.findImport_DetailByYmport(_import);
            List<Integer> exist_book_ids = existImportDetails.stream().map(existImportDetail-> existImportDetail.getBook().getId()).toList();
            List<Integer> book_ids = import_details.stream().map(import_detail -> import_detail.getBook().getId()).toList();
            existImportDetails.removeIf(import_detail -> !book_ids.contains(import_detail.getBook().getId()));
            for(Integer book_id:book_ids){
                if(!exist_book_ids.contains(book_id)){
                    import_details.forEach(import_detail -> {
                        if(import_detail.getBook().getId() ==book_id){
                            existImportDetails.add(import_detail);
                        }
                    });
                }
            }
            _import.getImport_detail().clear();
            _import.getImport_detail().addAll(existImportDetails);
        }
        int total_import_cost_all =
                Cal_Total_Import(_import.getImport_detail()).intValue();
        _import.setImport_total(total_import_cost_all);
        _import.setImport_status(false);
        return importRepository.save(_import);
    }
    @Secured("ROLE_ADMIN")
    public ImportResponse verifyImport(int id){
        try{
            if(!importRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Import existImport = importRepository.findImportById(id);
             existImport.setImport_status(true);
             existImport.getImport_detail().forEach(import_detail -> {
                 if(!bookRepository.existsById(import_detail.getBook().getId())){
                     throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
                 }
                 Book existBook =
                         bookRepository.findBookById(import_detail.getBook().getId());
                 float newQuantity =
                         existBook.getBookQuantity() + import_detail.getQuantity();
                 existBook.setBookQuantity(newQuantity);
             });
             return importMapper.toImportResponse(importRepository.save(existImport));
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_VERIFY);
        }
    }
}
