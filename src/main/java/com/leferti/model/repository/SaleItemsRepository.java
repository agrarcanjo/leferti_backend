package com.leferti.model.repository;

import com.leferti.api.dto.SaleItemCustomIDTO;
import com.leferti.model.entity.SaleItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SaleItemsRepository extends JpaRepository<SaleItems, Long> {

	Optional<SaleItems> findById(Long id);

	@Query( value =
			"SELECT SL.ID as idSaleItem, P.id as idProduct, SL.amount , P.price AS productPrice, P.name AS productName " +
					"FROM leferti.sale_items SL " +
					"INNER JOIN leferti.product P ON SL.id_product = P.id WHERE SL.id_sale = :idSale", nativeQuery = true)
	List<SaleItemCustomIDTO> findSaleItemsBySale(@Param("idSale") Long idSale);

	/**
	 * Delete all user with ids specified in {@code ids} parameter
	 *
	 * @param ids List of SaleItems ids
	 */
	@Modifying
	@Query("delete from SaleItems u where u.id in ?1")
	void deleteSaleItemsWithIds(long[] ids);

	void deleteByIdSale(Long id);
}
