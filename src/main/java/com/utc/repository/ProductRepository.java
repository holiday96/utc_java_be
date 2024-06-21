package com.utc.repository;

import com.utc.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 27.5.2024
 * Description :
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByUserId(Long userId, Pageable pageable);
    Page<Product> findByPriceBetween(Long minPrice, Long maxPrice, Pageable pageable);
    Page<Product> findByPriceGreaterThanEqual(Long minPrice, Pageable pageable);
    Page<Product> findByPriceLessThanEqual(Long maxPrice, Pageable pageable);
}
