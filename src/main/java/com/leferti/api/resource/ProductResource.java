package com.leferti.api.resource;

import com.leferti.api.dto.ProductDTO;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Product;
import com.leferti.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService service;

    @GetMapping
    public ResponseEntity find(
            @RequestParam(value ="product" , required = false) String find,
            @RequestParam(value ="description" , required = false) String description
    ) {
        Product productFilter = new Product();

        if(find!=null && !find.isEmpty() && find.matches("^[0-9]*$")){
            productFilter.setId(Long.parseLong(find));
        }else{
            productFilter.setName(find);
        }

        if(description!=null && !description.trim().equals(""))
            productFilter.setDescription(description);

        List<Product> product = service.find(productFilter);
        if(product.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto não encontrado.");
        }else
            return ResponseEntity.ok(product);
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
        product.setPrice(new BigDecimal(dto.getPrice()));
        product.setCost(new BigDecimal(dto.getCost()));
        product.setDateRegister(LocalDate.now());

        return product;
    }


}
