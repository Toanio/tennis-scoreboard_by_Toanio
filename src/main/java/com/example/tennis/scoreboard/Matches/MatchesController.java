package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.MatchDTOResponse;
import com.example.tennis.scoreboard.Matches.dto.MatchDTORequest;
import com.example.tennis.scoreboard.Matches.dto.MatchInfoDTO;
import com.example.tennis.scoreboard.Players.dto.PlayersDTORequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
         return ResponseEntity.status(200).body(matchesService.setPoint(uuid,request.name()));
    }
}
