package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role>findByRoleName(RoleName roleName);

    @Query(nativeQuery=true, value ="select roles_id as r from user_role as e where e.user_id = :user_id")
    Long getRoleId(@Param("user_id") Integer userId);
}
