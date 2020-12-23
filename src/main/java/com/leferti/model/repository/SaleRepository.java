package com.leferti.model.repository;

import com.leferti.api.dto.SaleCustomIDTO;
import com.leferti.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	Optional<Sale> findById(Long id);

	Optional<Sale> findByIdCustomer(Long idCustomer);

	@Query( value =
			"select s " +
					"from Sale s " +
					"join s.customer c " +
					"where upper(c.name) like :customerName "  +
					"order by s.dateRegister desc " )
	List<Sale> findSalesByCustomerName( @Param("customerName") String customerName);

	@Query( value =
			"select s.id as idSale, s.total, s.discount, c.name as customerName, c.phone, c.id as idCustomer," +
					" TO_CHAR(s.date_register, 'dd/MM/yyyy') as dateRegister, s.debt " +
					"from leferti.sale s " +
					"inner join leferti.customer c on s.id_customer = c.id " +
					"where upper(c.name) like :customerName " +
					"and s.debt= :isDebt "  +
					"order by s.date_register desc, " +
					"s.id desc ", nativeQuery = true)
	List<SaleCustomIDTO> findByCustomerName(@Param("customerName") String customerName, @Param("isDebt") Boolean isDebt);

	@Modifying(clearAutomatically = true)
	@Query("update Sale s set s.debt=:deby where s.id = :id")
	void switchDebt(@Param("id")Long id, @Param("deby") Boolean deby);

}
