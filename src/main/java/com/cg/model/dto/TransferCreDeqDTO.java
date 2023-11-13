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
public class TransferCreDeqDTO implements Validator {

    Long senderId;
    Long receipientId;
    String balance;
    String transferAmount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        TransferCreDeqDTO transferCreDeqDTO = (TransferCreDeqDTO) o;

        String transactionAmountString = transferCreDeqDTO.getTransferAmount();

        if (transactionAmountString.isBlank()) {
            errors.rejectValue("transferAmount", "transactionAmount.string", "Số tiền nhập không được bỏ trống");
        } else if (!transactionAmountString.matches("^[0-9]+$")) {
            errors.rejectValue("transferAmount", "transactionAmount.numberValid", "Chỉ được nhập ký tự số");
            return;
        }
        BigDecimal balance = BigDecimal.valueOf(Long.parseLong(transferCreDeqDTO.getBalance()));
        BigDecimal transferAmount = BigDecimal.valueOf(Long.parseLong(transferCreDeqDTO.getTransferAmount()));
        BigDecimal transactionAmount = transferAmount.multiply(BigDecimal.valueOf(1.1));
        boolean isInRange = transferAmount.compareTo(new BigDecimal("100000")) >= 0 && transferAmount.compareTo(new BigDecimal("1000000000")) <= 0;

        if (!isInRange) {
            errors.rejectValue("transferAmount", "transactionAmount.value", "Số tiền chuyển phải trong khoảng từ 100.000 đến 1.000.000.000");
            return;
        }

        if (transactionAmount.compareTo(balance) > 0) {
            errors.rejectValue("transferAmount", "transactionAmount.compare", "Số tiền trong tài khoản không đủ");
        }
    }
}