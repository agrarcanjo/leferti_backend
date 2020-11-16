package com.leferti.service.impl;

import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Product;
import com.leferti.model.repository.ProductRepository;
import com.leferti.service.ProductService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        validate(product);
        return repository.save(product);
    }

    @Override
    public void validate(Product product) {
        if(product.getName() == null || product.getName().trim().equals("")) {
            throw new RegraNegocioException("Informe um nome válido.");
        }
        if(product.getCost()== null || product.getCost().equals("")) {
            throw new RegraNegocioException("Informe um custo para o produto.");
        }

    }

    @Override
    public Optional<Product> findById(Long idProduct) {
        return repository.findById(idProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> find(Product product) {
        Example example = Example.of( product,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) );

        return repository.findAll(example);
    }

    @Override
    @Transactional
    public void delete(Product product) {
        Objects.requireNonNull(product.getId());
        repository.delete(product);
    }


}
