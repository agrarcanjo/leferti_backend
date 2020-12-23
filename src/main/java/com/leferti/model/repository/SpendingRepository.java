package com.leferti.model.repository;

import com.leferti.model.entity.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpendingRepository extends JpaRepository<Spending, Long> {

	Optional<Spending> findById(Long id);
}
