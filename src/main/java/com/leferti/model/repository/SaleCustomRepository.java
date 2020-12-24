package com.leferti.model.repository;

import com.leferti.api.dto.IndicatorsCustomDTO;
import com.leferti.api.dto.SaleCustomDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class SaleCustomRepository {

 	private final EntityManager em;

 	public SaleCustomRepository (EntityManager em) {
 		this.em = em;
	}

	public List<SaleCustomDTO> findByCustomCustomerName(String customerName, Boolean isDebt, String dateFilter) {
 		String query = "select new com.leferti.api.dto.SaleCustomDTO(s.id as idSale, s.total, s.discount, c.name as customerName, c.phone, c.id as idCustomer, " +
				"TO_CHAR(s.dateRegister, 'dd/MM/yyyy') as dateRegister, s.debt) " +
				"from Sale s " +
				"inner join Customer c on s.idCustomer = c.id ";
		String condiction = "where ";
 		if(!customerName.trim().equals("")){
 			query+= condiction + "upper(c.name) like :customerName ";
 			condiction = "and ";
		}

 		if(isDebt){
 			query+= condiction + "s.debt= :isDebt ";
 			condiction= "and ";
		}

 		if(dateFilter!=null && !dateFilter.trim().equals("")){
 			query+= condiction + "s.dateRegister = :date ";
		}

 		query+="order by s.dateRegister desc, " +
				"s.id desc ";

		TypedQuery<SaleCustomDTO> q = (TypedQuery<SaleCustomDTO>) em.createQuery(query);

		if(customerName!=null && !customerName.trim().equals("")){
			q.setParameter("customerName" , customerName.toUpperCase() + "%");
		}

		if(isDebt){
			q.setParameter("isDebt" , isDebt);
		}

		if(!dateFilter.trim().equals("")){
			q.setParameter("date", LocalDate.parse(dateFilter, DateTimeFormatter.ofPattern("d/MM/yyyy")));
		}

		return q.getResultList();
	}

	public IndicatorsCustomDTO findIndicators(){
 		String query =
				"  select " +
						"(select sum(s.total) - sum(s.discount) " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id) as valorVendaTotalBruta, " +
						" " +
						"(select sum(s.total - (s.discount)) " +
						" from leferti.sale s " +
						"          inner join leferti.sale_items sl on sl.id_sale = s.id where date_part('month', s.date_register) = date_part('month',now()))  as valorVendaTotalBrutaMes, " +
						" " +
						"(select sum(s.total - (s.discount) - (p.cost * sl.amount)) as valorVendaTotalLiquita " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id " +
						"    inner join leferti.product p on p.id = sl.id_product), " +
						" " +
						"(select sum(s.total - (s.discount) - (p.cost * sl.amount)) as valorVendaTotalLiquitaMes " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id " +
						"    inner join leferti.product p on p.id = sl.id_product where date_part('month', s.date_register) = date_part('month',now())), " +
						" " +
						"       (select CAST(sum(sl.amount) AS INT) as qntProdutosVendidos " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on s.id = sl.id_sale), " +
						" " +
						"(select sum(s.total) as valorTotalFiado " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id where s.debt is true), " +
						" " +
						"(select CAST(count(s.id) AS INT) as qntVendasFiado " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id where s.debt is true), " +
						" " +
						"(select CAST(count(c.id) AS INT) as qntClientesCadastrados " +
						"    from leferti.customer c ), " +
						" " +
						"(select sum(s.price) as custoTotais " +
						"    from leferti.spending s ), " +
						" " +
						"(select sum(s.price) as custoTotaisMes " +
						"    from leferti.spending s where date_part('month', s.date_register) = date_part('month',now())) " +
						" ;";


		List<BigDecimal[]> items = em.createNativeQuery(query).getResultList();

		IndicatorsCustomDTO indicador = new IndicatorsCustomDTO();

		int i = 0;
		Object[] item = items.get(0);

		indicador.setValorVendaTotalBruta((BigDecimal) item[i ++]);
		indicador.setValorVendaTotalBrutaMes((BigDecimal) item[i ++]);
		indicador.setValorVendaTotalLiquida((BigDecimal) item[i ++]);
		indicador.setValorVendaTotalLiquidaMes((BigDecimal) item[i ++]);
		indicador.setQntProdutosVendidos((Integer) item[i ++]);
		indicador.setValorTotalFiado((BigDecimal) item[i ++]);
		indicador.setQntVendasFiado((Integer) item[i ++]);
		indicador.setQntClientesCadastrados((Integer) item[i ++]);
		indicador.setCustoTotais((BigDecimal) item[i ++]);
		indicador.setCustoTotaisMes((BigDecimal) item[i ++]);

		return indicador;
	}


}
