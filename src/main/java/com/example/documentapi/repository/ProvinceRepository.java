package com.example.documentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.documentapi.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
    Province findByCode(String code);
}
