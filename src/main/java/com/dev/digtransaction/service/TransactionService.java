package com.dev.digtransaction.service;

import com.dev.digtransaction.domain.account.Account;
import com.dev.digtransaction.domain.transaction.Transaction;
import com.dev.digtransaction.dto.TransactionRequest;
import com.dev.digtransaction.exception.AccountNotFoundException;
import com.dev.digtransaction.exception.InsufficientBalanceException;
import com.dev.digtransaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public Transaction createTransaction(TransactionRequest trxRequest)
            throws AccountNotFoundException, InsufficientBalanceException {
        Account senderAccount = accountService.findAccountById(trxRequest.accountSenderId());
        Account receiverAccount = accountService.findAccountById(trxRequest.accountReceiverId());

        BigDecimal trxValue = trxRequest.value();

        validateTransaction(senderAccount, trxValue);

        Transaction newTrx = new Transaction(trxValue, senderAccount, receiverAccount, LocalDateTime.now());
        repository.save(newTrx);

        // Update sender account
        senderAccount.setBalance(senderAccount.getBalance().subtract(trxValue));
        accountService.save(senderAccount);

        // Update receiver account
        receiverAccount.setBalance(receiverAccount.getBalance().add(trxValue));
        accountService.save(receiverAccount);

        return newTrx;
    }

    private void validateTransaction(Account sender, BigDecimal trxValue) {
        BigDecimal senderBalance = sender.getBalance();
        if(senderBalance.compareTo(trxValue) < 0) {
            String message = "Account " + sender.getId() + " does not have sufficient balance";
            throw new InsufficientBalanceException(message);
        }
    }

}
