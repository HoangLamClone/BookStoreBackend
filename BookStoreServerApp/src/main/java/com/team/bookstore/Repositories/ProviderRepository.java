package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Provider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Integer> {
    Provider findProviderById(int id);
    Boolean existsById(int id);
    List<Provider> findAll(Specification<Provider> spec);
}
