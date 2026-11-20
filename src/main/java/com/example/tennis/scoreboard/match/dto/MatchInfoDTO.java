package com.example.tennis.scoreboard.match.dto;

public record MatchInfoDTO(
        ActivePlayerDTO firstPlayer,
        ActivePlayerDTO secondPlayer,
        String winnerName

) {
}
