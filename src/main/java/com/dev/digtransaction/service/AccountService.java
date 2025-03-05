package com.dev.digtransaction.service;

import com.dev.digtransaction.domain.account.Account;
import com.dev.digtransaction.domain.user.User;
import com.dev.digtransaction.dto.AccountRequest;
import com.dev.digtransaction.exception.AccountNotFoundException;
import com.dev.digtransaction.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository repository;

    public Account createAccount(AccountRequest request) {
        User accountOwner = userService.findUserById(request.userId());
        Account account = new Account(accountOwner);
        accountOwner.setAccount(account);
        repository.save(account);
        return account;
    }

    public void save(Account account) {
        repository.save(account);
    }

    public Account findAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account id " + id + " not found in database"));
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    /**
     * IMPORTANT:
     * This method is a helper to facilitate tests.
     * It should not be used in the business logic.
     */
    public void addValueToAccount(Long accountId, BigDecimal value) {
        Account account = findAccountById(accountId);
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(value);
        account.setBalance(newBalance);
        repository.save(account);
    }

}
