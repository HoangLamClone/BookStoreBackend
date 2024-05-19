package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Permission;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {
    Permission findPermissionByPermissionname(String permissionname);
    Boolean existsPermissionByPermissionname(String permissionname);
    List<Permission> findAll(Specification<Permission> spec);
}
