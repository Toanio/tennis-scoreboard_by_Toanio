package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.*;
import com.example.tennis.scoreboard.Players.dto.PlayersDTORequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchesController {
    private final MatchesService matchesService;

    public MatchesController(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @PostMapping
    public ResponseEntity<MatchDTOResponse> createMatch(
            @RequestBody MatchDTORequest request
    ) {
        return ResponseEntity.status(201).body(new MatchDTOResponse(matchesService.createMatch(request)));
    }

    @PostMapping("/{uuid}/point")
    public ResponseEntity<MatchInfoDTO> setPoint(
            @PathVariable UUID uuid,
            @RequestBody PlayersDTORequest request
    ) {
        return ResponseEntity.status(200).body(matchesService.setPoint(uuid, request.name()));
    }

    @GetMapping
    public ResponseEntity<MatchesDTO> getFinishedMatches(
            @RequestParam(required = false, name = "player_name") String playerName,
            Pageable pageable
    ) {
        return ResponseEntity.status(200).body(matchesService.allFinishMatches(playerName, pageable));
    }

}
