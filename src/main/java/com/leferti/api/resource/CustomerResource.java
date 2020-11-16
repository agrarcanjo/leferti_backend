package com.leferti.api.resource;

import com.leferti.api.dto.CustomerDTO;
import com.leferti.exception.ErroAutenticacao;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Customer;
import com.leferti.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerResource {

	private final CustomerService service;
	//private final LancamentoService lancamentoService;

	@PostMapping("/auth")
	public ResponseEntity auth(@RequestBody CustomerDTO dto ) {
		try {
			Customer customerAutenticado = service.auth(dto.getEmail(), dto.getPass());
			return ResponseEntity.ok(customerAutenticado);
		}catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity save(@RequestBody CustomerDTO dto ) {
		
		Customer customer = Customer.builder()
					.name(dto.getName())
					.email(dto.getEmail())
					.pass(dto.getPass()).build();
		
		try {
			Customer customerSalvo = service.saveCustomer(customer);
			return new ResponseEntity(customerSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

	/*
	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo(@PathVariable("id") Long id ) {
		Optional<Customer> customer = service.obterPorId(id);
		
		if(!customer.isPresent()) {
			return new ResponseEntity( HttpStatus.NOT_FOUND );
		}
		
		BigDecimal saldo = lancamentoService.obterSaldoPorCustomer(id);
		return ResponseEntity.ok(saldo);
	}*/

}
