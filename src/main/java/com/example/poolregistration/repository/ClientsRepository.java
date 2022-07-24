package com.example.poolregistration.repository;

import com.example.poolregistration.model.dao.PoolClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<PoolClient, Long> {

    List<PoolClient> findAllByName(String name);
}
