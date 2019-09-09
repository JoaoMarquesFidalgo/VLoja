package com.tqs.vloja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tqs.vloja.classes.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmailAndPassword(String email, String password);
}
