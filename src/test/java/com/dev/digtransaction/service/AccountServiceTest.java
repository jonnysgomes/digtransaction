package com.dev.digtransaction.service;

import com.dev.digtransaction.domain.account.Account;
import com.dev.digtransaction.domain.user.User;
import com.dev.digtransaction.domain.user.UserType;
import com.dev.digtransaction.dto.AccountRequest;
import com.dev.digtransaction.exception.AccountNotFoundException;
import com.dev.digtransaction.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create account successfully")
    void shouldCreateAccountSuccessfully() {
        User owner = new User(1L, "Jonnys", "Gomes",
                "02874589633", "jonnys@email.com", "123456", UserType.COMMON);

        AccountRequest accountRequest = new AccountRequest(owner.getId());
        when(userService.findUserById(owner.getId())).thenReturn(owner);
        Account newAccount = accountService.createAccount(accountRequest);

        verify(accountRepository, times(1)).save(newAccount);

        assertThat(newAccount).isNotNull();
        assertThat(newAccount.getOwner()).isEqualTo(owner);
    }

    @Test
    @DisplayName("Should return account when id exists")
    void shouldReturnAccountWhenIdExists() {
        User user = new User(1L, "Jonnys", "Gomes",
                "02874589633", "jonnys@email.com", "123456", UserType.COMMON);
        Long accountId = 1L;
        Account account = new Account(accountId, "51775-5", user, new BigDecimal(100.0), LocalDate.now());

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.findAccountById(accountId);

        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo(accountId);
        assertThat(foundAccount.getBalance()).isEqualTo(new BigDecimal(100.0));
    }

    @Test
    @DisplayName("Should throw exception when id does not exist")
    void shouldThrowExceptionWhenIdDoesNotExist() {
        Long nonExistentId = 1L;
        when(accountRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.findAccountById(nonExistentId))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account id " + nonExistentId + " not found in database");
    }

}