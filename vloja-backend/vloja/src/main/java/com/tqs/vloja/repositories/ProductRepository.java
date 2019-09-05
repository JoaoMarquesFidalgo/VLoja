package com.tqs.vloja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tqs.vloja.classes.Product;;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
