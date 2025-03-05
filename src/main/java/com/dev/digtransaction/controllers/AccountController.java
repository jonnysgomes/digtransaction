package com.dev.digtransaction.controllers;

import com.dev.digtransaction.domain.account.Account;
import com.dev.digtransaction.dto.AddMoneyToAccountRequest;
import com.dev.digtransaction.dto.AccountRequest;
import com.dev.digtransaction.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("new")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequest request) {
        Account newAccount = accountService.createAccount(request);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("addMoney")
    public ResponseEntity<String> addMoney(@RequestBody AddMoneyToAccountRequest request) {
        accountService.addValueToAccount(request.accountId(), request.value());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
