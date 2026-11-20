package com.example.tennis.scoreboard.match.dto;

public record ActivePlayerDTO(
        String name,
        String points,
        Integer games,
        Integer sets,
        Integer tieBreakPoints
) {
}
