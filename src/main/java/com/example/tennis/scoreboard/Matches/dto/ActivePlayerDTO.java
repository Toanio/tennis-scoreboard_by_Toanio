package com.example.tennis.scoreboard.Matches.dto;

public record ActivePlayerDTO(
        String name,
        String points,
        Integer games,
        Integer sets,
        Integer tieBreakPoints
) {
}
