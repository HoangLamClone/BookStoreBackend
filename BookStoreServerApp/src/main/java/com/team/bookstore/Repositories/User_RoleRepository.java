package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_RoleRepository extends JpaRepository<User_Role,String> {

}
