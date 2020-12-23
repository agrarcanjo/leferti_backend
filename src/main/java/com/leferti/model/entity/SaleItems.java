package com.leferti.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sale_items", schema = "leferti")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "id_product")
	private Long idProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_product", insertable = false, updatable = false)
	private Product product;

	@Column(name = "productPrice")
	private BigDecimal productPrice;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "id_sale")
	private Long idSale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sale", insertable = false, updatable = false)
	private Sale sale;

}
