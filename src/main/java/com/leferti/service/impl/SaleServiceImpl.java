package com.leferti.service.impl;

import com.leferti.api.dto.SaleCustomIDTO;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Sale;
import com.leferti.model.repository.SaleRepository;
import com.leferti.service.SaleService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private SaleRepository repository;

    public SaleServiceImpl(SaleRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public Sale save(Sale sale) {
        validate(sale);
        return repository.save(sale);
    }

    @Override
    public void validate(Sale sale) {
        if(sale.getIdCustomer()==0) {
            throw new RegraNegocioException("Informe um cliente válido.");
        }

        if(sale.getDateRegister()==null){
            throw new RegraNegocioException("Informe uma data válida.");
        }


    }

    @Override
    public Optional<Sale> findById(Long idSale) {
        return repository.findById(idSale);
    }

    @Override
    public Optional<Sale> findByIdCustomer(Long idCustomer) {
        return repository.findByIdCustomer(idCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sale> find(Sale sale) {
        Example example = Example.of( sale,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        return repository.findAll(example);
    }

    @Override
    @Transactional
    public void delete(Sale sale) {
        Objects.requireNonNull(sale.getId());
        repository.delete(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sale> findSalesByCustomerName(String customerName){
        return repository.findSalesByCustomerName(customerName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleCustomIDTO> findByCustomerName(String customerName, Boolean isDebt){
        return repository.findByCustomerName(customerName, isDebt);
    }

    @Override
    @Transactional
    public void switchDebt(Long id, Boolean deby) {
        repository.switchDebt(id, deby);
    }

    @Override
    public BigDecimal findTotalSale() {
        return null;
    }

}
