package com.example.documentapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.documentapi.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {

}
