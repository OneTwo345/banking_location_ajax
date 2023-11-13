package com.cg.controller.rest;

import com.cg.model.Customer;
import com.cg.model.Withdraw;
import com.cg.model.dto.DepositCreReqDTO;
import com.cg.model.dto.WithdrawCreDeqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.service.withdraw.IWithdrawService;
import com.cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/withdraws")
public class WithdrawResController {

    private AppUtils appUtils;
    private IWithdrawService withdrawService;
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getALl() {
        List<Withdraw> withdraws = withdrawService.findAll();
        return new ResponseEntity<>(withdraws, HttpStatus.OK);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawCreDeqDTO withdrawCreDeqDTO, BindingResult bindingResult, @PathVariable Long customerId) {
        new WithdrawCreDeqDTO().validate(withdrawCreDeqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }


        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customerService.findById(customerId).get());
        withdraw.setTransactionAmount(BigDecimal.valueOf(Long.valueOf(withdrawCreDeqDTO.getTransactionAmount())));
        withdraw.setDeleted(false);
        withdraw.setCreatedAt(LocalDate.now());
        withdrawService.save(withdraw);
        Customer customer = customerService.findById(customerId).get();
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}