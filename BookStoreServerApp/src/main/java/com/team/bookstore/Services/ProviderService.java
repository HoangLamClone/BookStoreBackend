package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.ProviderResponse;
import com.team.bookstore.Dtos.Responses.PublisherResponse;
import com.team.bookstore.Entities.Provider;
import com.team.bookstore.Entities.Publisher;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.ProviderMapper;
import com.team.bookstore.Repositories.ProviderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.ProviderSpecification.CreateProviderKeywordSpec;
import static com.team.bookstore.Specifications.PublisherSpecification.CreatePublisherKeywordSpec;

@Service
@Log4j2
public class ProviderService {
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ProviderMapper providerMapper;
    public List<ProviderResponse> getAllProvider(){
        try {
            return providerRepository.findAll().stream().map(providerMapper::toProviderResponse
            ).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<ProviderResponse> findProvidersBy(String keyword){
        try{
            Specification<Provider> spec = CreateProviderKeywordSpec(keyword);
            return providerRepository.findAll(spec).stream().map(providerMapper::toProviderResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public ProviderResponse createProvider(Provider provider){
        try{
            return providerMapper.toProviderResponse(providerRepository.save(provider));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public ProviderResponse updateProvider(int id,Provider provider){
        try{
            if(!providerRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            provider.setId(id);
            return providerMapper.toProviderResponse(providerRepository.save(provider));
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public ProviderResponse deleteProvider(int id){
        try{
            if(!providerRepository.existsById(id)){
                throw new ApplicationException(ErrorCodes.OBJECT_NOT_EXIST);
            }
            Provider existProvider =
                    providerRepository.findProviderById(id);
            providerRepository.delete(existProvider);
            return providerMapper.toProviderResponse(existProvider);
        }catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.CANNOT_DELETE);
        }
    }
}
