package com.leferti.api.resource;

import com.leferti.api.dto.SaleCustomDTO;
import com.leferti.api.dto.SaleDTO;
import com.leferti.api.dto.SaleItemCustomIDTO;
import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Sale;
import com.leferti.model.entity.SaleItems;
import com.leferti.model.repository.SaleCustomRepository;
import com.leferti.service.SaleItemsService;
import com.leferti.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleResource {

    private final SaleService service;
    private final SaleItemsService serviceSaleItems;
    private final SaleCustomRepository saleCustomRepository;


    @PostMapping
    public ResponseEntity save(@RequestBody SaleDTO dto ) {
        try {
            Sale entidade = converter(dto);
            entidade = service.save(entidade);
            for(int i=0; i<dto.getProductsId().length;i++){
                SaleItems saleItems = new SaleItems();
                saleItems.setIdProduct(Long.parseLong(dto.getProductsId()[i]));
                saleItems.setAmount(Integer.parseInt(dto.getProductsAmount()[i]));
                saleItems.setIdSale(entidade.getId());
                saleItems.setProductPrice(new BigDecimal(dto.getProductsPrice()[i]));
                serviceSaleItems.save(saleItems);
            }
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void removeSaleItems(SaleDTO dto){
        serviceSaleItems.deleteSaleItemsByIds(Arrays.asList(dto.getIdsSaleItem()).stream().mapToLong(Long::parseLong).toArray());
    }

    private Sale converter( SaleDTO saleDTO){
        return Sale.builder().idCustomer(saleDTO.getCustomer())
                .total(!saleDTO.getTotal().equals("") ? new BigDecimal(saleDTO.getTotal()): null)
                .discount(!saleDTO.getDiscount().equals("") ? new BigDecimal(saleDTO.getDiscount()) : new BigDecimal(0))
                .dateRegister(!saleDTO.getRegistrationDate().equals("") ? LocalDate.parse(saleDTO.getRegistrationDate(), DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")) : null)
                .debt(saleDTO.getDebt()!=null ? saleDTO.getDebt() : false)
                .build();
    }

    @GetMapping
    public ResponseEntity find(
            @RequestParam(value ="customer" , required = false) String customer,
            @RequestParam(value ="isDebt" , required = false) Boolean isDebt,
            @RequestParam(value ="dateFilter" , required = false) String dateFilter,
            @RequestParam(value ="dateFilterEnd" , required = false) String dateFilterEnd,
            @RequestParam(value ="page" , required = false) Integer page
    ) {
        List<SaleCustomDTO> sales = saleCustomRepository.
                findByCustomCustomerName(customer, isDebt, dateFilter, dateFilterEnd, page);
        if(!sales.isEmpty()){
            return ResponseEntity.ok(sales);
        } else{
            return ResponseEntity.badRequest().body("Nenhuma venda encontrada!");
        }
    }

    @GetMapping("saleItems/{id}")
    public ResponseEntity findSaleItems(@PathVariable("id") Long id ) {
        List<SaleItemCustomIDTO> saleItems = serviceSaleItems.findSaleItemsBySale(id);
        if(saleItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Itens não encontrados para o Cliente");
        }else {
            return ResponseEntity.ok(saleItems);
        }
    }

    @GetMapping("indicators")
    public ResponseEntity findIndicators() {
        return ResponseEntity.ok(saleCustomRepository.findIndicators());
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id ) {
        serviceSaleItems.deleteByIdSale(id);
        return service.findById(id).map( entidade -> {
            service.delete(entidade);
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }).orElseGet( () ->
                new ResponseEntity("Venda não encontrada na base de Dados.", HttpStatus.BAD_REQUEST) );
    }


    @PostMapping("/debt")
    public ResponseEntity debt(@RequestBody SaleDTO dto) {
        try{
            service.switchDebt(dto.getId(), dto.getDebt());
            return ResponseEntity.ok().body("ok");
        }catch (Exception e ){
            return ResponseEntity.badRequest().body("Produto não encontrado.");
        }
    }



}
