package com.leferti.api.resource;

import com.leferti.api.dto.CustomerDTO;
import com.leferti.exception.ErroAutenticacao;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Customer;
import com.leferti.model.entity.Product;
import com.leferti.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerResource {

	private final CustomerService service;

	@PostMapping("/auth")
	public ResponseEntity auth(@RequestBody CustomerDTO dto ) {
			//Customer customerAutenticado = service.auth(dto.getEmail(), dto.getPass());
			if (Objects.nonNull(dto.getEmail()) && dto.getEmail().equals("gloria")
					&& Objects.nonNull(dto.getPassword()) && dto.getPassword().equals("leferti")) {
				Customer customer = Customer.builder().id(1L).email(dto.getEmail()).name("gloria").build();
				return ResponseEntity.ok().header( "authorization","kdlsADklalsSDFmvclkDASLdADKAUEJNCNAalaksodkx").body(customer);
			} else {
				return ResponseEntity.badRequest().body("Usuário não encontrado");
			}
	}

	@GetMapping
	public ResponseEntity find(
			@RequestParam(value ="find" , required = false) String find
	) {
		Customer customerFilter = new Customer();

		if(!find.isEmpty() && find.matches("^[0-9]*$")){
			customerFilter.setId(Long.parseLong(find));
		}else{
			customerFilter.setName(find);
		}

		List<Customer> customer = service.find(customerFilter);
		if(customer.isEmpty()) {
			return ResponseEntity.badRequest().body("Produto não encontrado.");
		}else
			return ResponseEntity.ok(customer);
	}
	
	@PostMapping
	public ResponseEntity save(@RequestBody CustomerDTO dto ) {
		
		Customer customer = Customer.builder()
					.name(dto.getName())
					.email(dto.getEmail())
					.cpf(dto.getCpf())
					.phone(dto.getPhone())
					.id(dto.getId())
				.build();
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
