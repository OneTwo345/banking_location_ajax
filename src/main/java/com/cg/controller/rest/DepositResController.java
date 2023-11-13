package com.cg.controller.rest;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.model.dto.CustomerCreReqDTO;
import com.cg.model.dto.DepositCreReqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.withdraw.IWithdrawService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/deposits")
public class DepositResController {
    @Autowired
    private   IDepositService depositService;
    @Autowired
    private   ICustomerService customerService;
    @Autowired
    private  AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getALl() {
        List<Deposit> deposits = depositService.findAll();
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }
    @PostMapping("/{customerId}")
    public ResponseEntity<?> deposit(@RequestBody DepositCreReqDTO depositCreReqDTO, BindingResult bindingResult,@PathVariable Long customerId) {
        new DepositCreReqDTO().validate(depositCreReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }


        Deposit deposit = new Deposit();
        deposit.setCustomer(customerService.findById(customerId).get());
        deposit.setTransactionAmount(BigDecimal.valueOf(Long.valueOf(depositCreReqDTO.getTransactionAmount())));
        deposit.setDeleted(false);
        deposit.setCreatedAt(LocalDate.now());
        depositService.save(deposit);
        Customer customer = customerService.findById(customerId).get();
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}
