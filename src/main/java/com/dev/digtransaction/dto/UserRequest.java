package com.dev.digtransaction.dto;

import com.dev.digtransaction.domain.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String document,
        @Email String email,
        @NotBlank @Size(min = 6, message = "Password must have a min of 6 characters") String password,
        @NotNull UserType userType) {
}
