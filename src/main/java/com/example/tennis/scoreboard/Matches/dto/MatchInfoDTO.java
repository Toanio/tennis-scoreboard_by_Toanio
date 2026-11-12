package com.example.tennis.scoreboard.Matches.dto;

public record MatchInfoDTO(
        ActivePlayerDTO firstPlayer,
        ActivePlayerDTO secondPlayer,
        String winnerName

) {
}
