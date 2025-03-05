package com.dev.digtransaction.domain.account;

import com.dev.digtransaction.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Entity(name = "Account")
@Table(name = "accounts")
@NoArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User owner;

    private BigDecimal balance;

    private LocalDate createdAt;

    public Account(User owner) {
        this.owner = owner;
        this.accountNumber = generateAccountNumber();
        this.balance = new BigDecimal(0.0);
        this.createdAt = LocalDate.now();
    }

    /**
     * IMPORTANT:
     * This constructor is used for test purposes.
     * It should not be used to any other reason.
     */
    @Deprecated
    public Account(Long id, String accountNumber, User owner, BigDecimal balance, LocalDate createdAt) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    private String generateAccountNumber() {
        int accountNumber = new Random().nextInt(999999);
        return String.format("%06d", accountNumber);
    }

}
