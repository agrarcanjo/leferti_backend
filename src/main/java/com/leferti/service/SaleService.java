package com.leferti.service;

import com.leferti.api.dto.SaleCustomIDTO;
import com.leferti.model.entity.Sale;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SaleService {

    Sale save(Sale sale);

    void validate(Sale sale);

    Optional<Sale> findById(Long idSale);

    Optional<Sale> findByIdCustomer(Long idCustomer);

    List<Sale> find(Sale sale);

    @Transactional
    void delete(Sale sale);

    @Transactional(readOnly = true)
    List<Sale> findSalesByCustomerName(String customerName);

    @Transactional(readOnly = true)
    List<SaleCustomIDTO> findByCustomerName(String customerName, Boolean isDebt);

    void switchDebt(Long id, Boolean deby);

    BigDecimal findTotalSale();

}
