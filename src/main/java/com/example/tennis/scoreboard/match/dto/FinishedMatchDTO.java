package com.example.tennis.scoreboard.match.dto;

public record FinishedMatchDTO(
        String firstPlayerName,
        String secondPlayerName,
        String winnerName
) {
}
