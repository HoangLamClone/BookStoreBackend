package com.team.bookstore.Services;
import com.team.bookstore.Dtos.Responses.CategoryResponse;
import com.team.bookstore.Entities.Category;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.CategoryMapper;
import com.team.bookstore.Repositories.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.CategorySpecification.CreateCategoryKeywordSpec;
import static com.team.bookstore.Specifications.ProviderSpecification.CreateProviderKeywordSpec;

@Service
@Log4j2
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMapper categoryMapper;
    public List<CategoryResponse> getAllCategories(){
        try {
            return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<CategoryResponse> findCategoriesBy(String keyword){
        try{
            Specification<Category> spec = CreateCategoryKeywordSpec(keyword);
            return categoryRepository.findAll(spec).stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public CategoryResponse createCategory(MultipartFile image,
                                           Category category){
        try{
            category.setAvatar(image.getBytes());
            return categoryMapper.toCategoryResponse(categoryRepository.save(category));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public CategoryResponse updateCategory(int id,MultipartFile image,
                                           Category category){
        try{
            category.setAvatar(image.getBytes());
            if(!categoryRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            category.setId(id);
            return categoryMapper.toCategoryResponse(categoryRepository.save(category));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public CategoryResponse deleteCategory(int id){
        try{
            if(!categoryRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Category existCategory =
                    categoryRepository.findCategoryById(id);
            categoryRepository.delete(existCategory);
            return categoryMapper.toCategoryResponse(existCategory);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
