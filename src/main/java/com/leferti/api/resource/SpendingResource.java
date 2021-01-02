package com.leferti.api.resource;

import com.leferti.api.dto.*;
import com.leferti.api.util.StringUtil;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Product;
import com.leferti.model.entity.Spending;
import com.leferti.model.repository.SpendingCustomRepository;
import com.leferti.service.SpendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/spending")
@RequiredArgsConstructor
public class SpendingResource {

    private final SpendingService service;
    private final SpendingCustomRepository serviceCustomRepository;


    @PostMapping
    public ResponseEntity save(@RequestBody SpendingDTO dto ) {
        try {
            Spending entidade = converter(dto);
            entidade = service.save(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity find(
            @RequestParam(value ="name" , required = false) String name,
            @RequestParam(value ="description" , required = false) String description,
            @RequestParam(value ="month" , required = false) String month,
            @RequestParam(value ="year" , required = false) String year
    ) {
        List<Spending> spending = serviceCustomRepository.findCustomSpending(name, description, month, year);
        if(spending.isEmpty()) {
            return ResponseEntity.badRequest().body("Gasto não encontrado");
        }else {
            return ResponseEntity.ok(spending);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id ) {
        return service.findById(id).map( entidade -> {
            service.delete(entidade);
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }).orElseGet( () ->
                new ResponseEntity("Não encontrada na base de Dados.", HttpStatus.BAD_REQUEST) );
    }


    private Product converter(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(new BigDecimal(dto.getPrice()));
        product.setCost(new BigDecimal(dto.getCost()));

        return product;
    }

    private Spending converter( SpendingDTO dto){
        return Spending.builder()
                .price(StringUtil.formatMoney(dto.getPrice()))
                .description(dto.getDescription())
                .dateRegister(!dto.getDateRegister().equals("") ? LocalDate.parse(dto.getDateRegister(), DateTimeFormatter.ofPattern("d/MM/yyyy")) : null)
                .amount(dto.getAmount())
                .name(dto.getName())
                .build();
    }



}
