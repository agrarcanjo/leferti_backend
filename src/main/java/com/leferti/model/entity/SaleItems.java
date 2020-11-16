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

	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;

	@Column(name = "amount")
	private Integer amount;

	@ManyToOne
	@JoinColumn(name = "id_sale")
	private Sale sale;

}
