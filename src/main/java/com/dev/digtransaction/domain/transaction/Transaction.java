package com.dev.digtransaction.domain.transaction;

import com.dev.digtransaction.domain.account.Account;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Transactions")
@Table(name = "transactions")
@NoArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @OneToOne
    private Account sender;

    @OneToOne
    private Account receiver;

    private LocalDateTime timestamp;

    public Transaction(BigDecimal amount, Account sender, Account receiver, LocalDateTime timestamp) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }
}
