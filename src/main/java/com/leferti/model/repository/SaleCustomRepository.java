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
import java.util.Objects;

@Repository
public class SaleCustomRepository {

 	private final EntityManager em;

 	public SaleCustomRepository (EntityManager em) {
 		this.em = em;
	}

	public Long countSalesByFilter(String customerName, Boolean isDebt, String dateFilter ) {
		String query = "select count(s.id) " +
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

		TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery(query);

		if(customerName!=null && !customerName.trim().equals("")){
			q.setParameter("customerName" , customerName.toUpperCase() + "%");
		}

		if(isDebt){
			q.setParameter("isDebt" , isDebt);
		}

		if(!dateFilter.trim().equals("")){
			q.setParameter("date", LocalDate.parse(dateFilter, DateTimeFormatter.ofPattern("d/MM/yyyy")));
		}

		return q.getSingleResult();
	}

	public List<SaleCustomDTO> findByCustomCustomerName(String customerName, Boolean isDebt, String dateFilter, String dateFilterEnd, Integer pageNumber) {
 		String query = "select new com.leferti.api.dto.SaleCustomDTO(s.id as idSale, s.total, s.discount, c.name as customerName, c.phone, c.id as idCustomer, " +
				"DATE_FORMAT(s.dateRegister, '%d/%m/%Y') as dateRegister, s.debt) " +
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

 		if(Objects.nonNull(dateFilterEnd) && !dateFilterEnd.trim().equals("")){
			query+= condiction + "s.dateRegister BETWEEN :date and :dateEnd ";
		}else{
			if(Objects.nonNull(dateFilter) && !dateFilter.trim().equals("")){
				query+= condiction + "s.dateRegister = :date ";
			}
		}

 		query+="order by s.dateRegister desc, " +
				"s.id desc ";

		TypedQuery<SaleCustomDTO> q = (TypedQuery<SaleCustomDTO>) em.createQuery(query);

		if(!customerName.trim().equals("")){
			q.setParameter("customerName" , customerName.toUpperCase() + "%");
		}

		if(isDebt){
			q.setParameter("isDebt" , isDebt);
		}

		if(Objects.nonNull(dateFilterEnd) && !dateFilterEnd.trim().equals("")){
			q.setParameter("date", LocalDate.parse(dateFilter, DateTimeFormatter.ofPattern("d/M/yyyy")));
			q.setParameter("dateEnd", LocalDate.parse(dateFilterEnd, DateTimeFormatter.ofPattern("d/M/yyyy")));
		}else{
			if(!dateFilter.trim().equals("")) {
				q.setParameter("date", LocalDate.parse(dateFilter, DateTimeFormatter.ofPattern("d/M/yyyy")));
			}
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
						"          inner join leferti.sale_items sl on sl.id_sale = s.id where MONTH(s.date_register) = MONTH(now()))  as valorVendaTotalBrutaMes, " +
						" " +
						"(select sum(s.total - (s.discount) - (p.cost * sl.amount)) as valorVendaTotalLiquita " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id " +
						"    inner join leferti.product p on p.id = sl.id_product), " +
						" " +
						"(select sum(s.total - (s.discount) - (p.cost * sl.amount)) as valorVendaTotalLiquitaMes " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id " +
						"    inner join leferti.product p on p.id = sl.id_product where MONTH(s.date_register) = MONTH(now())), " +
						" " +
						"       (select CAST(sum(sl.amount) AS UNSIGNED) as qntProdutosVendidos " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on s.id = sl.id_sale), " +
						" " +
						"(select sum(s.total) as valorTotalFiado " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id where s.debt is true), " +
						" " +
						"(select CAST(count(s.id) AS UNSIGNED) as qntVendasFiado " +
						"    from leferti.sale s " +
						"    inner join leferti.sale_items sl on sl.id_sale = s.id where s.debt is true), " +
						" " +
						"(select CAST(count(c.id) AS UNSIGNED) as qntClientesCadastrados " +
						"    from leferti.customer c ), " +
						" " +
						"(select sum(s.price) as custoTotais " +
						"    from leferti.spending s ), " +
						" " +
						"(select sum(s.price) as custoTotaisMes " +
						"    from leferti.spending s where MONTH(s.date_register) = MONTH(now())) " +
						" ;";


		List<BigDecimal[]> items = em.createNativeQuery(query).getResultList();

		IndicatorsCustomDTO indicador = new IndicatorsCustomDTO();

		int i = 0;
		Object[] item = items.get(0);

		indicador.setValorVendaTotalBruta((BigDecimal) item[i ++]);
		indicador.setValorVendaTotalBrutaMes((BigDecimal) item[i ++]);
		indicador.setValorVendaTotalLiquida((BigDecimal) item[i ++]);
		indicador.setValorVendaTotalLiquidaMes((BigDecimal) item[i ++]);
		indicador.setQntProdutosVendidos((BigInteger) item[i ++]);
		indicador.setValorTotalFiado((BigDecimal) item[i ++]);
		indicador.setQntVendasFiado((BigInteger) item[i ++]);
		indicador.setQntClientesCadastrados((BigInteger) item[i ++]);
		indicador.setCustoTotais((BigDecimal) item[i ++]);
		indicador.setCustoTotaisMes((BigDecimal) item[i ++]);

		return indicador;
	}


}
