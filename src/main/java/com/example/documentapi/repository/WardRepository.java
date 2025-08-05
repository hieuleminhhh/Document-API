package com.example.documentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.documentapi.entity.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {
    Ward findByCode(String code);
}
