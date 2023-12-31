package com.cg.service.withdraw;

import com.cg.model.Withdraw;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import com.cg.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WithdrawServiceImpl implements IWithdrawService {

    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Withdraw> findAll() {
        return null;
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Withdraw withdraw) {
        customerRepository.decrementBalance(withdraw.getCustomer().getId(),withdraw.getTransactionAmount());
        withdrawRepository.save(withdraw);
    }

    @Override
    public void update(Withdraw withdraw) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
