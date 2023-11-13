package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.service.customer.ICustomerService;
import com.cg.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transfers")
public class TransferController {
    @Autowired
    private ITransferService transferService;

    @GetMapping
    public String showListPage(Model model) {
        List<Transfer> transfers = transferService.findAll();
        model.addAttribute("transfers", transfers);
        return "customer/transferList";
    }
}
