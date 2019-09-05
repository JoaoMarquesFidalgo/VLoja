package com.tqs.vloja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tqs.vloja.classes.List_;

public interface ListRepository extends JpaRepository<List_, Integer> {
}