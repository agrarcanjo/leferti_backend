package com.leferti.service;

import com.leferti.api.dto.SaleItemCustomIDTO;
import com.leferti.model.entity.SaleItems;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SaleItemsService {

    SaleItems save(SaleItems sale);

    void validate(SaleItems sale);

    Optional<SaleItems> findById(Long idSale);

    List<SaleItems> find(SaleItems sale);

    @Transactional
    void delete(SaleItems sale);

    @Transactional
    void deleteByIdSale(Long id);

    @Transactional(readOnly = true)
    List<SaleItemCustomIDTO> findSaleItemsBySale(Long idSale);

    @Transactional
    void deleteSaleItemsByIds(long[] ids);
}
