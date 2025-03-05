package com.dev.digtransaction.dto;

import java.math.BigDecimal;

public record AddMoneyToAccountRequest(Long accountId, BigDecimal value) {
}
