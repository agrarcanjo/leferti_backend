package com.leferti.service.impl;

import com.leferti.exception.ErroAutenticacao;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Customer;
import com.leferti.model.repository.CustomerRepository;
import com.leferti.service.CustomerService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository repository;
	
	public CustomerServiceImpl(CustomerRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Customer auth(String email, String senha) {
		Optional<Customer> customer = repository.findByEmail(email);
		
		if(!customer.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
		}
		
		if(!customer.get().getPass().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}

		return customer.get();
	}

	@Override
	@Transactional
	public Customer saveCustomer(Customer customer) {
		validate(customer);
		return repository.save(customer);
	}

	private void validate(Customer customer) {
		if(customer.getName() == null || customer.getName().trim().equals("")){
			throw new RegraNegocioException("Nome é obrigatório");
		}
	}

	@Override
	public void validateEmail(String email) {
		if(email == null || email.trim().equals("")) {
			throw new RegraNegocioException("E-mail Obrigatório");
		}

		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> find(Customer customer){
		Example example = Example.of( customer,
				ExampleMatcher.matching()
						.withIgnoreCase()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) );

		return repository.findAll(example);
	}


}
