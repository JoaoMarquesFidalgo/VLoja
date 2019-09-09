package com.tqs.vloja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tqs.vloja.classes.ProductList;;

public interface ProductListRepository extends JpaRepository<ProductList, Integer> {
	ProductList findByProductIdAndListId(Integer productId, Integer ListId);
}
