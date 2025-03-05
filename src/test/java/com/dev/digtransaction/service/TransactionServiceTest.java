package com.dev.digtransaction.service;

import com.dev.digtransaction.domain.account.Account;
import com.dev.digtransaction.domain.transaction.Transaction;
import com.dev.digtransaction.domain.user.User;
import com.dev.digtransaction.domain.user.UserType;
import com.dev.digtransaction.dto.TransactionRequest;
import com.dev.digtransaction.exception.InsufficientBalanceException;
import com.dev.digtransaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionrepository;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should get all created transactions")
    void shouldReturnAllTransactions() {
        User sender = new User(1L, "Jonnys", "Gomes",
                "02874589633", "jonnys@email.com", "123456", UserType.COMMON);
        User receiver = new User(2L, "Guilherme", "Gomes",
                "02874589634", "guilherme@email.com", "654321", UserType.COMMON);

        Account senderAccount = new Account(1L, "51775-5", sender, new BigDecimal(100.0), LocalDate.now());
        Account receiverAccount = new Account(2L, "51776-6", receiver, new BigDecimal(0.0), LocalDate.now());

        when(accountService.findAccountById(1L)).thenReturn(senderAccount);
        when(accountService.findAccountById(2L)).thenReturn(receiverAccount);

        TransactionRequest trxDTO1 = new TransactionRequest(new BigDecimal(10.0), 1L, 2L);
        Transaction newTrx1 = transactionService.createTransaction(trxDTO1);

        TransactionRequest trxDTO2 = new TransactionRequest(new BigDecimal(20.0), 1L, 2L);
        Transaction newTrx2 = transactionService.createTransaction(trxDTO2);

        when(transactionrepository.findAll()).thenReturn(List.of(newTrx1, newTrx2));

        List<Transaction> transactions = transactionService.getAll();

        assertThat(transactions).isNotNull().hasSize(2);
        assertThat(transactions).containsExactly(newTrx1, newTrx2);
    }

    @Test
    @DisplayName("Create transaction method should create transaction successfully")
    void createTransactionSuccessfully() {
        User sender = new User(1L, "Jonnys", "Gomes",
                "02874589633", "jonnys@email.com", "123456", UserType.COMMON);
        User receiver = new User(2L, "Guilherme", "Gomes",
                "02874589634", "guilherme@email.com", "654321", UserType.COMMON);

        Account senderAccount = new Account(1L, "51775-5", sender, new BigDecimal(100.0), LocalDate.now());
        Account receiverAccount = new Account(2L, "51776-6", receiver, new BigDecimal(0.0), LocalDate.now());

        when(accountService.findAccountById(1L)).thenReturn(senderAccount);
        when(accountService.findAccountById(2L)).thenReturn(receiverAccount);

        TransactionRequest trxDTO = new TransactionRequest(new BigDecimal(90.0), 1L, 2L);
        Transaction newTrx = transactionService.createTransaction(trxDTO);

        assertThat(newTrx).isNotNull();

        verify(transactionrepository, times(1)).save(newTrx);
        verify(accountService, times(1)).save(senderAccount);
        verify(accountService, times(1)).save(receiverAccount);

        assertThat(senderAccount.getBalance()).isEqualTo(new BigDecimal(10.0));
        assertThat(receiverAccount.getBalance()).isEqualTo(new BigDecimal(90.0));
    }

    @Test
    @DisplayName("Create transaction method should throw InsufficientBalanceException")
    void createTransactionThrowInsufficientBalanceException() {
        User sender = new User(1L, "Jonnys", "Gomes",
                "02874589633", "jonnys@email.com", "123456", UserType.COMMON);
        User receiver = new User(2L, "Guilherme", "Gomes",
                "02874589634", "guilherme@email.com", "654321", UserType.COMMON);

        Account senderAccount = new Account(1L, "51775-5", sender, new BigDecimal(100.0), LocalDate.now());
        Account receiverAccount = new Account(2L, "51776-6", receiver, new BigDecimal(0.0), LocalDate.now());

        when(accountService.findAccountById(1L)).thenReturn(senderAccount);
        when(accountService.findAccountById(2L)).thenReturn(receiverAccount);

        Exception ex = assertThrows(InsufficientBalanceException.class, () -> {
            TransactionRequest trxDTO = new TransactionRequest(new BigDecimal(200.0), 1L, 2L);
            transactionService.createTransaction(trxDTO);
        });

        assertEquals("Account " + senderAccount.getId() + " does not have sufficient balance", ex.getMessage());
    }
}