package com.example.tennis.scoreboard.players;

import com.example.tennis.scoreboard.players.dto.PlayersDTO;
import com.example.tennis.scoreboard.players.dto.PlayersDTORequest;
import com.example.tennis.scoreboard.exceptions.MessageDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayersController {
    private final PlayersService service;

    public PlayersController(PlayersService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MessageDto> createPlayers(
            @RequestBody @Valid PlayersDTORequest request
    ) {
        return ResponseEntity.status(201).body(service.createPlayer(request));
    }

    @GetMapping
    public List<PlayersDTO> showAllPlayers(
            Pageable pageable
    ) {
        return service.showAllPlayers(pageable);
    }

}
