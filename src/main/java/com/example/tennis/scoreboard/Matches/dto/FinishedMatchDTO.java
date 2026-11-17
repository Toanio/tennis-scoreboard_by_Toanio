package com.example.tennis.scoreboard.Matches.dto;

public record FinishedMatchDTO(
        String firstPlayerName,
        String secondPlayerName,
        String winnerName
) {
}
