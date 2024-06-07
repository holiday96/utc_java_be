package com.utc.repository;

import com.utc.models.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
@Repository
public interface NewRepository extends JpaRepository<New, Long> {
    Page<New> findAllByUserId(Long userId, Pageable pageable);

    Optional<New> findByTitle(String title);
}
