package com.utc.repository;

import com.utc.contants.ERole;
import com.utc.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 27.5.2024
 * Description :
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
