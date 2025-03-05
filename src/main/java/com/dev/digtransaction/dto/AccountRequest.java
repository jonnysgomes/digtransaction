package com.dev.digtransaction.dto;

import jakarta.validation.constraints.NotNull;

public record AccountRequest(
        @NotNull Long userId) {
}
