package com.leferti.model.repository;

import com.leferti.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	boolean existsByEmail(String email);
	
	Optional<Customer> findByEmail(String email);
	
}
