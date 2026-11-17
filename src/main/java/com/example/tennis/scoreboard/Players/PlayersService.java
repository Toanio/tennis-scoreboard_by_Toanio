package com.example.tennis.scoreboard.Players;

import com.example.tennis.scoreboard.Players.dto.PlayersDTO;
import com.example.tennis.scoreboard.Players.dto.PlayersDTORequest;
import com.example.tennis.scoreboard.exceptions.MessageDto;
import com.example.tennis.scoreboard.exceptions.PlayerAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayersService {
    private final PlayersRepository playersRepository;
    private final PlayersMapper playersMapper;

    public PlayersService(PlayersRepository playersRepository, PlayersMapper playersMapper) {
        this.playersRepository = playersRepository;
        this.playersMapper = playersMapper;
    }

    public MessageDto createPlayer(PlayersDTORequest request) {
        Players findPlayer = playersRepository.findByNameIgnoreCase(request.name());
        if (findPlayer != null) {
            throw new PlayerAlreadyExistsException("Players already exist");
        }
        playersRepository.save(new Players(request.name()));
        return new MessageDto("Player successfully created");
    }

    public List<PlayersDTO> showAllPlayers() {
        List<Players> players = playersRepository.findAll();
        return playersMapper.mapToDto(players);
        //TODO: сделать экономичный поиск всех игроков
    }

    public Players findPlayerByName(String name) {
        return playersRepository.findByNameIgnoreCase(name);
    }

}
