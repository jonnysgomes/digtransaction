package com.dev.digtransaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(
        @DecimalMin(value = "1.00", message = "The transaction min value is 1.00") BigDecimal value,
        @NotNull Long accountSenderId,
        @NotNull Long accountReceiverId) {
}
