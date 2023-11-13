package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WithdrawCreDeqDTO implements Validator {
    String balance;
    String transactionAmount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        WithdrawCreDeqDTO withdrawCreDeqDTO = (WithdrawCreDeqDTO) o;
        String transactionAmountString = withdrawCreDeqDTO.getTransactionAmount();


        if (transactionAmountString.isBlank()) {
            errors.rejectValue("transactionAmount", "transactionAmount.string", "Số tiền nhập không được bỏ trống");
        } else if (!transactionAmountString.matches("^[0-9]+$")) {
            errors.rejectValue("transactionAmount", "transactionAmount.numberValid", "Chỉ được nhập ký tự số");
            return;
        }
        BigDecimal balance = BigDecimal.valueOf(Long.parseLong(withdrawCreDeqDTO.getBalance()));
        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(withdrawCreDeqDTO.getTransactionAmount()));
        boolean isInRange = transactionAmount.compareTo(new BigDecimal("100000")) >= 0 && transactionAmount.compareTo(new BigDecimal("1000000000")) <= 0;
        if (!isInRange) {
            errors.rejectValue("transactionAmount", "transactionAmount.value", "Số tiền chuyển phải trong khoảng từ 100.000 đến 1.000.000.000");
            return;
        }
        if (transactionAmount.compareTo(balance) == -1) {
            errors.rejectValue("transactionAmount", "transactionAmount.compare", " Số dư không đ");
        }
    }
}
