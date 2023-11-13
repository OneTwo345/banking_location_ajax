package com.cg.service.deposit;

import com.cg.model.Deposit;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class DepositServiceImpl implements IDepositService {

    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Deposit> findAll() {
        return null;
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return null;
    }

    @Override
    public void save(Deposit deposit) {
        customerRepository.incrementBalance(deposit.getCustomer().getId(),deposit.getTransactionAmount());
        depositRepository.save(deposit);
    }

    @Override
    public void update(Deposit deposit) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
