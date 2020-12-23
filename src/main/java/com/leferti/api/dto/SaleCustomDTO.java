package com.leferti.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleCustomDTO {
    private Long idSale;
    private BigDecimal total;
    private BigDecimal discount;
    private String customerName;
    private String phone;
    private Long idCustomer;
    private String dateRegister;
    private Boolean debt;
}
