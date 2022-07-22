package com.example.poolregistration.repository;

import com.example.poolregistration.model.dao.PoolOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<PoolOrder, Long> {

    List<PoolOrder> findAllByReserveDate(LocalDate reserveDate);
}
