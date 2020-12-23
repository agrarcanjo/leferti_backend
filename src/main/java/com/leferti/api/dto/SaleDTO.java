package com.leferti.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private Long customer;
    private String registrationDate;
    private String total;
    private String discount;
    private String[] productsId;
    private String[] productsAmount;
    private String[] productsPrice;
    private String[] idsSaleItem;
    private Boolean debt;

}
