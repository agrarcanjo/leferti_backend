package com.leferti.api.dto;

import java.math.BigDecimal;

public interface SaleCustomIDTO {
    Long getIdSale();
    Long getIdCustomer();
    String getCustomerName();
    String getDateRegister();
    BigDecimal getTotal();
    String getPhone();
    BigDecimal getDiscount();
    Boolean getDebt();

}
