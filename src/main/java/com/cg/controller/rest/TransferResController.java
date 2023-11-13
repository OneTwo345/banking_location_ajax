package com.cg.controller.rest;

import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.model.dto.DepositCreReqDTO;
import com.cg.model.dto.TransferCreDeqDTO;
import com.cg.model.dto.WithdrawCreDeqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.service.transfer.ITransferService;
import com.cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transfers")
public class TransferResController {
    private AppUtils appUtils;
    private ITransferService transferService;
    private ICustomerService customerService;
    @GetMapping
    public ResponseEntity<?> getALl() {
        List<Transfer> transfers = transferService.findAll();
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
    @PostMapping("/{customerId}")
    public ResponseEntity<?> transfer(@RequestBody TransferCreDeqDTO transferCreDeqDTO, BindingResult bindingResult, @PathVariable Long customerId) {

        new TransferCreDeqDTO().validate(transferCreDeqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }


        Transfer transfer = new Transfer();
        transfer.setSender(customerService.findById(customerId).get());
        transfer.setRecipient(customerService.findById(transferCreDeqDTO.getReceipientId()).get());
        transfer.setTransferAmount(BigDecimal.valueOf(Long.valueOf(transferCreDeqDTO.getTransferAmount())));
        transfer.setDeleted(false);
        transfer.setCreatedAt(LocalDate.now());
        transferService.save(transfer);

        Customer customer = customerService.findById(customerId).get();
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}