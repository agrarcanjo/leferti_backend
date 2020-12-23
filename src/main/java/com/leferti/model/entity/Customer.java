package com.leferti.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table( name = "customer" , schema = "leferti")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "pass")
	@JsonIgnore
	private String pass;


}
