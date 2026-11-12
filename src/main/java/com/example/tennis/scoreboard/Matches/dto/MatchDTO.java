package com.example.tennis.scoreboard.Matches.dto;

public record MatchDTO(
        Integer player1,
        Integer player2,
        Integer winner
) {
}
//TODO: переделать нейминг dto