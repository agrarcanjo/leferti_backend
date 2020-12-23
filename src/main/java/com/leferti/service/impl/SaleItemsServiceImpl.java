package com.leferti.service.impl;

import com.leferti.api.dto.SaleItemCustomIDTO;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.SaleItems;
import com.leferti.model.repository.SaleItemsRepository;
import com.leferti.service.SaleItemsService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SaleItemsServiceImpl implements SaleItemsService {

    private SaleItemsRepository repository;

    public SaleItemsServiceImpl(SaleItemsRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public SaleItems save(SaleItems saleItems) {
        validate(saleItems);
        return repository.save(saleItems);
    }

    @Override
    public void validate(SaleItems sale) {
        if(sale.getIdProduct() == null) {
            throw new RegraNegocioException("Nenhum produto selecionado");
        }
    }

    @Override
    public Optional<SaleItems> findById(Long idSale) {
        return repository.findById(idSale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleItems> find(SaleItems sale) {
        Example example = Example.of( sale,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) );

        return repository.findAll(example);
    }

    @Override
    @Transactional
    public void delete(SaleItems sale) {
        Objects.requireNonNull(sale.getId());
        repository.delete(sale);
    }

    @Override
    @Transactional
    public void deleteByIdSale(Long id) {
        repository.deleteByIdSale(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleItemCustomIDTO> findSaleItemsBySale(Long idSale){
        return repository.findSaleItemsBySale(idSale);
    }

    @Transactional
    @Override
    public void deleteSaleItemsByIds(long[] ids) {
        repository.deleteSaleItemsWithIds(ids);
    }

}
