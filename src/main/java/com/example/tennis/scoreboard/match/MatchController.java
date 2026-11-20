package com.example.tennis.scoreboard.match;

import com.example.tennis.scoreboard.match.dto.MatchDTORequest;
import com.example.tennis.scoreboard.match.dto.MatchDTOResponse;
import com.example.tennis.scoreboard.match.dto.MatchInfoDTO;
import com.example.tennis.scoreboard.match.dto.MatchesDTO;
import com.example.tennis.scoreboard.player.dto.PlayersDTORequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/matches")
@Validated
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<MatchDTOResponse> createMatch(
            @RequestBody @Valid MatchDTORequest request
    ) {
        return ResponseEntity.status(201).body(new MatchDTOResponse(matchService.createMatch(request)));
    }

    @PostMapping("/{uuid}/point")
    public ResponseEntity<MatchInfoDTO> setPoint(
            @PathVariable UUID uuid,
            @RequestBody @Valid PlayersDTORequest request
    ) {
        return ResponseEntity.status(200).body(matchService.setPoint(uuid, request.name()));
    }

    @GetMapping
    public ResponseEntity<MatchesDTO> getFinishedMatches(
            @RequestParam(required = false, name = "player_name")
            @Size(min = 2, max = 30, message = "Player name must be between 2 and 30 characters")
            @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9]+$", message = "Player name must not contain special characters")
            String playerName,
            Pageable pageable
    ) {
        return ResponseEntity.status(200).body(matchService.allFinishMatches(playerName, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MatchInfoDTO> getMatchScore(
            @PathVariable UUID uuid
    ) {
        return ResponseEntity.status(200).body(matchService.getMatchScore(uuid));
    }

}
