package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.ComposeKey.RolePermissionKey;
import com.team.bookstore.Entities.Role_Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Role_PermissionRepository extends JpaRepository<Role_Permission, RolePermissionKey> {
}
