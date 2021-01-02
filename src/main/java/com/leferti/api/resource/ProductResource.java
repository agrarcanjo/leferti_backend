package com.leferti.api.resource;

import com.leferti.api.dto.ProductDTO;
import com.leferti.api.util.StringUtil;
import com.leferti.exception.RegraNegocioException;
import com.leferti.exception.ResourceException;
import com.leferti.model.entity.Product;
import com.leferti.model.repository.ProductCustomRepository;
import com.leferti.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService service;
    private final ProductCustomRepository productCustomRepository;

    @GetMapping
    public ResponseEntity<Page<Product>> find(
            @RequestParam(value ="product" , required = false) String product,
            @RequestParam(value ="description" , required = false) String description,
            @RequestParam(value ="page" , required = false, defaultValue = "0") Integer page
    ) {
        Page<Product> products = productCustomRepository.findAllWithFilters(product, description, page);

        if(!products.isEmpty())
            return new ResponseEntity<>(products, HttpStatus.OK);
        else
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Produto não encontrado");
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductDTO dto ) {
        try {
            Product entidade = converter(dto);
            entidade = service.saveProduct(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ProductDTO converter(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    private Product converter(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(StringUtil.formatMoney(dto.getPrice()));
        product.setCost(StringUtil.formatMoney(dto.getCost()));
        product.setDateRegister(LocalDate.now());

        return product;
    }


}
