package com.example.tennis.scoreboard.matches.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MatchDTORequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
        @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9]+$", message = "Name must not contain special characters")
        String firstPlayerName,

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
        @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9]+$", message = "Name must not contain special characters")
        String secondPlayerName
) {
}
