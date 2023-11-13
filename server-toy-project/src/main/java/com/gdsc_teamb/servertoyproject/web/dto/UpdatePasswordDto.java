package com.gdsc_teamb.servertoyproject.web.dto;

import jakarta.validation.constraints.Pattern;

public record UpdatePasswordDto(String checkPassword, String toBePassword) {
}
