package com.example.tennis.scoreboard.matches.dto;

public record ActivePlayerDTO(
        String name,
        String points,
        Integer games,
        Integer sets,
        Integer tieBreakPoints
) {
}
