package com.leferti.service;

import com.leferti.model.entity.Spending;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SpendingService {

    @Transactional
    Spending save(Spending spending);

    void validate(Spending sp);

    Optional<Spending> findById(Long id);

    @Transactional(readOnly = true)
    List<Spending> find(Spending s);

    @Transactional
    void delete(Spending s);
}
