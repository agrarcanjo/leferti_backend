package com.leferti.model.repository;

import com.leferti.api.util.Constants;
import com.leferti.model.entity.Product;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductCustomRepository  {

 	private final EntityManager em;
 	private final CriteriaBuilder cb;

 	public ProductCustomRepository(EntityManager em) {
 		this.em = em;
 		this.cb = em.getCriteriaBuilder();
	}

	public Page<Product> findAllWithFilters(String product, String description, Integer page){
 		CriteriaQuery<Product> criteriaQuery = cb.createQuery(Product.class);
		Root<Product> productRoot = criteriaQuery.from(Product.class);
		Predicate predicate = getPredicate(product, description, productRoot);
		criteriaQuery.where(predicate);
		setOrder(productRoot, criteriaQuery);
		TypedQuery<Product> typedQuery = em.createQuery(criteriaQuery);
		typedQuery.setFirstResult(page * Constants.SIZE_PAGE);
		typedQuery.setMaxResults(Constants.SIZE_PAGE);

		Pageable pageable = getPageable(page);
		long productCount = getProductCount(predicate);

		return new PageImpl<>(typedQuery.getResultList(), pageable, productCount);
	}

	private Predicate getPredicate(String product, String description, Root<Product> productRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if(Objects.nonNull(product) && !product.isEmpty()) {
			if (product.matches("^[0-9]*$")) {
				predicates.add(cb.equal(productRoot.get("id"), product));
			} else {
				predicates.add(cb.like(cb.upper(productRoot.get("name")), "%" + product + "%"));
			}
		}
		if(Objects.nonNull(description)){
			predicates.add(cb.like(cb.upper(productRoot.get("description")), "%" + description + "%"));
		}
		return cb.and(predicates.toArray(new Predicate[0]));
	}

	private void setOrder(Root<Product> productRoot, CriteriaQuery<Product> criteriaQuery) {
 		criteriaQuery.orderBy(cb.desc(productRoot.get("dateRegister")), cb.desc(productRoot.get("id")));
	}

	private Pageable getPageable(Integer page) {
 		Sort sort = Sort.by(Sort.Direction.DESC, "dateRegister", "id");
 		return PageRequest.of(page, Constants.SIZE_PAGE, sort );
	}

	private long getProductCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Product> countRoot = countQuery.from(Product.class);
		countQuery.select(cb.count(countRoot)).where(predicate);
		return em.createQuery(countQuery).getSingleResult();
 	}

}
