package com.example.tennis.scoreboard.Matches.dto;

import com.example.tennis.scoreboard.Matches.Points;

public record ActivePlayerDTO(
        String name,
        String points,
        Integer games,
        Integer sets,
        Integer tieBreakPoints
) {
}
