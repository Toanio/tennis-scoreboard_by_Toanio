package com.example.tennis.scoreboard.Players;

import com.example.tennis.scoreboard.Players.dto.PlayersDTO;
import com.example.tennis.scoreboard.Players.dto.PlayersDTORequest;
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
    public void createPlayers(
            @RequestBody PlayersDTORequest request
    ) {
        service.createPlayer(request);
        //TODO: сделать информативный вывод
    }

    @GetMapping
    public List<PlayersDTO> showAllPlayers() {
        return service.showAllPlayers();
    }

}
