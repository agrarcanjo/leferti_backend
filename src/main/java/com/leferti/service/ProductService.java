package com.leferti.service;

import com.leferti.model.entity.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveProduct(Product product);

    void validate(Product product);

    Optional<Product> findById(Long idProduct);

    List<Product> find(Product product);

    @Transactional
    void delete(Product product);

}
