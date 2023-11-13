package com.cg.service.transfer;

import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.repository.CustomerRepository;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class TransferServiceImpl implements ITransferService{
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return null;
    }

    @Override
    public void save(Transfer transfer) {
        transfer.setFees(Long.valueOf(10));
        transfer.setFeesAmount(transfer.getTransferAmount().multiply(BigDecimal.valueOf(0.1)));
        transfer.setTransactionAmount(transfer.getFeesAmount().add(transfer.getTransferAmount()));

        customerRepository.decrementBalance(transfer.getSender().getId(),transfer.getTransactionAmount());
        customerRepository.incrementBalance(transfer.getRecipient().getId(),transfer.getTransferAmount());

        transferRepository.save(transfer);
    }

    @Override
    public void update(Transfer transfer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
