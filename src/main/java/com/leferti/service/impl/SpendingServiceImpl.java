package com.leferti.service.impl;

import com.leferti.exception.RegraNegocioException;
import com.leferti.model.entity.Spending;
import com.leferti.model.repository.SpendingRepository;
import com.leferti.service.SpendingService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpendingServiceImpl implements SpendingService {

    private SpendingRepository repository;

    public SpendingServiceImpl(SpendingRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public Spending save(Spending spending) {
        return repository.save(spending);
    }

    @Override
    public void validate(Spending sp) {
        if(sp.getName()==null && sp.getName().equals("")) {
            throw new RegraNegocioException("Informe um cliente válido.");
        }

    }

    @Override
    public Optional<Spending> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Spending> find(Spending s) {
        Example example = Example.of( s,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)

        );
        return repository.findAll(example, Sort.by(Sort.Direction.DESC, "dateRegister"));
    }

    @Override
    @Transactional
    public void delete(Spending s) {
        Objects.requireNonNull(s.getId());
        repository.delete(s);
    }


}
