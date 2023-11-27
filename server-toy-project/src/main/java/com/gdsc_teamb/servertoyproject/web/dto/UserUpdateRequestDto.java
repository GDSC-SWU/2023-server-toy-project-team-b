package com.gdsc_teamb.servertoyproject.web.dto;

import java.util.Optional;

public record UserUpdateRequestDto(Optional<String> nickname, Optional<String> phone) {
}