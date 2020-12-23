package com.leferti.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorsCustomDTO {
    private BigDecimal valorVendaTotalBruta;
    private BigDecimal valorVendaTotalBrutaMes;
    private BigDecimal valorVendaTotalLiquida;
    private BigDecimal valorVendaTotalLiquidaMes;
    private Integer qntProdutosVendidos;
    private BigDecimal valorTotalFiado;
    private Integer qntVendasFiado;
    private Integer qntClientesCadastrados;
    private BigDecimal custoTotais;
    private BigDecimal custoTotaisMes;

}
