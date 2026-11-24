package com.example.tennis.scoreboard.player;

import com.example.tennis.scoreboard.player.dto.PlayersDTO;
import com.example.tennis.scoreboard.player.dto.PlayersDTORequest;
import com.example.tennis.scoreboard.exception.MessageDto;
import com.example.tennis.scoreboard.exception.PlayerAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Transactional
    public MessageDto createPlayer(PlayersDTORequest request) {
        Player findPlayer = playerRepository.findByNameIgnoreCase(request.name());
        if (findPlayer != null) {
            throw new PlayerAlreadyExistsException("Players already exist");
        }
        playerRepository.save(new Player(request.name()));
        return new MessageDto("Player successfully created");
    }

    @Transactional
    public Player createPlayerByName(String name) {
        Player findPlayer = playerRepository.findByNameIgnoreCase(name);
        if (findPlayer != null) {
            throw new PlayerAlreadyExistsException("Players already exist");
        }
        return playerRepository.save(new Player(name));
    }

    @Transactional(readOnly = true)
    public List<PlayersDTO> showAllPlayers(Pageable pageable) {
        Page<Player> players = playerRepository.findAll(pageable);
        return playerMapper.mapToDto(players);
    }

    public Player findPlayerByName(String name) {
        return playerRepository.findByNameIgnoreCase(name);
    }

}
