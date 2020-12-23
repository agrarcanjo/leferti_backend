package com.leferti.model.entity;

import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sale", schema = "leferti")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "total")
	private BigDecimal total;

	@Column(name = "discount")
	private BigDecimal discount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_customer", updatable = false, insertable = false)
	private Customer customer;

	@Column(name = "id_customer")
	private Long idCustomer;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sale_items")
	private List<SaleItems> saleItems;

	@Column(name = "date_register")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dateRegister;

	@Column(name = "debt")
	private Boolean debt;
 }
