package com.leferti.service;

import com.leferti.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

	Customer auth(String email, String senha);

	Customer saveCustomer(Customer customer);

	void validateEmail(String email);

	Optional<Customer> findById(Long id);

	List<Customer> find(Customer customer);

}
