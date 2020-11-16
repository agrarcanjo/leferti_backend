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
@Table(name = "stock", schema = "leferti")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	
	@Column(name = "amount")
	private Integer amount;

	@Column(name = "cost")
	private BigDecimal cost;
	
	@Column(name = "date_register")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dateRegister;

}
