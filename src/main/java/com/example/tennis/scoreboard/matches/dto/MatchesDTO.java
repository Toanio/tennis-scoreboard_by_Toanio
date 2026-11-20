package com.example.tennis.scoreboard.matches.dto;

import java.util.List;

public record MatchesDTO(
        List<FinishedMatchDTO> matches,
        Integer currentPage,
        Integer totalPages
) {
}
