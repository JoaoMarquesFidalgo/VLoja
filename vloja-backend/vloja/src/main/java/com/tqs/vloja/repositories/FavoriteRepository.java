package com.tqs.vloja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;	
import com.tqs.vloja.classes.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
}
