package com.leferti.model.repository;

import com.leferti.model.entity.Spending;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class SpendingCustomRepository {

 	private final EntityManager em;

 	public SpendingCustomRepository(EntityManager em) {
 		this.em = em;
	}

	public List<Spending> findCustomSpending(String name, String description, String month, String year) {
 		String query = "select s " +
				"from Spending s ";
		String condiction = "where ";
 		if(!name.trim().equals("")){
 			query+= condiction + "upper(s.name) like :name ";
 			condiction = "and ";
		}

 		if(!description.trim().equals("")){
 			query+= condiction + "upper(s.description) = :description ";
 			condiction= "and ";
		}

 		if(!month.trim().equals("")){
 			query+= condiction + "MONTH(s.dateRegister) = :month ";
			condiction= "and ";
		}

		if(!year.trim().equals("")){
			query+= condiction + "YEAR(s.dateRegister) = :year ";
		}

 		query+="order by s.dateRegister desc ";

		TypedQuery<Spending> q = em.createQuery(query, Spending.class);

		if(!name.trim().equals("")){
			q.setParameter("name", name.toUpperCase() + "%");
		}

		if(!description.trim().equals("")){
			q.setParameter("description", description.toUpperCase() + "%");
		}

		if(!month.trim().equals("")){
			q.setParameter("month", Integer.parseInt(month));
		}

		if(!year.trim().equals("")){
			q.setParameter("year",  Integer.parseInt(year));
		}

		return q.getResultList();
	}


}
