package com.dev.digtransaction.controllers;

import com.dev.digtransaction.domain.transaction.Transaction;
import com.dev.digtransaction.dto.TransactionRequest;
import com.dev.digtransaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "new")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction newTrx = transactionService.createTransaction(request);
        return new ResponseEntity<>(newTrx, HttpStatus.CREATED);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> trxList = transactionService.getAll();
        return new ResponseEntity<>(trxList, HttpStatus.OK);
    }
}
