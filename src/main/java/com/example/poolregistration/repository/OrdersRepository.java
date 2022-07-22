package com.example.poolregistration.repository;

import com.example.poolregistration.model.dao.PoolOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<PoolOrder, Long> {
}
