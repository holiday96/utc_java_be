package com.utc.repository;

import com.utc.models.News;
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
public interface NewsRepository extends JpaRepository<News, Integer> {
    Page<News> findAllByUser_Id(Long userId, Pageable pageable);

    Optional<News> findByTitle(String title);

    Optional<News> findByIdAndStatus(Integer id, Integer code);
}
