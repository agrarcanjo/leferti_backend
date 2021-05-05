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
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorsCustomDTO {
    private BigDecimal valorVendaTotalBruta;
    private BigDecimal valorVendaTotalBrutaMes;
    private BigDecimal valorVendaTotalLiquida;
    private BigDecimal valorVendaTotalLiquidaMes;
    private BigInteger qntProdutosVendidos;
    private BigDecimal valorTotalFiado;
    private BigInteger qntVendasFiado;
    private BigInteger qntClientesCadastrados;
    private BigDecimal custoTotais;
    private BigDecimal custoTotaisMes;

}
