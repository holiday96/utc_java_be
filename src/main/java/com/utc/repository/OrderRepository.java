package com.utc.repository;

import com.utc.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable);

    Page<Order> findByUserId(Long userId, Pageable pageable);
}
