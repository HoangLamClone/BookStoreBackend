package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Role findRoleByRolename(String rolename);
    Boolean existsRoleByRolename(String rolename);
    List<Role> findAll(Specification<Role> spec);
}
