package com.example.tennis.scoreboard.matches.dto;

public record FinishedMatchDTO(
        String firstPlayerName,
        String secondPlayerName,
        String winnerName
) {
}
