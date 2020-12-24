package com.leferti.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product", schema = "leferti")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "cost")
	private BigDecimal cost;

	@ManyToOne
	@JoinColumn(name = "id_product_category")
	private ProductCategory productCategory;
	
	@Column(name = "date_register")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dateRegister;

	@Transient
	private Integer qnt;

	/*
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusLancamento status; */
}
