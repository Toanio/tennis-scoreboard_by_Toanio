package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.*;
import com.example.tennis.scoreboard.Players.dto.PlayersDTORequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
@Validated
public class MatchesController {
    private final MatchesService matchesService;

    public MatchesController(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @PostMapping
    public ResponseEntity<MatchDTOResponse> createMatch(
            @RequestBody @Valid MatchDTORequest request
    ) {
        return ResponseEntity.status(201).body(new MatchDTOResponse(matchesService.createMatch(request)));
    }

    @PostMapping("/{uuid}/point")
    public ResponseEntity<MatchInfoDTO> setPoint(
            @PathVariable UUID uuid,
            @RequestBody @Valid PlayersDTORequest request
    ) {
        return ResponseEntity.status(200).body(matchesService.setPoint(uuid, request.name()));
    }

    @GetMapping
    public ResponseEntity<MatchesDTO> getFinishedMatches(
            @RequestParam(required = false, name = "player_name")
            @NotBlank(message = "playerName cannot be blank")
            @Size(min = 2, max = 30, message = "Player name must be between 2 and 30 characters")
            @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9]+$", message = "Player name must not contain special characters")
            String playerName,
            Pageable pageable
    ) {
        return ResponseEntity.status(200).body(matchesService.allFinishMatches(playerName, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MatchInfoDTO> getMatchScore(
            @PathVariable UUID uuid
    ) {
        return ResponseEntity.status(200).body(matchesService.getMatchScore(uuid));
    }

}
