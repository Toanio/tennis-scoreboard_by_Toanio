package com.example.tennis.scoreboard.matches.dto;

public record MatchInfoDTO(
        ActivePlayerDTO firstPlayer,
        ActivePlayerDTO secondPlayer,
        String winnerName

) {
}
