package com.dev.digtransaction.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String errorMessage, HttpStatus statusCode) {
}
