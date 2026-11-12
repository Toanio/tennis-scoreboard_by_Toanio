package com.example.tennis.scoreboard.Players;

import com.example.tennis.scoreboard.Players.dto.PlayersDTO;
import com.example.tennis.scoreboard.Players.dto.PlayersDTORequest;
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

    public void createPlayer(PlayersDTORequest request) {
        Players findPlayer = playersRepository.findByName(request.name());
        if (findPlayer != null) {
            throw new IllegalArgumentException("Players already exist");
            //TODO: сделать проверку на пустое поле с правильной ошибкой
        }
        playersRepository.save(new Players(request.name()));
        //TODO: сделать проаверку уникальности игроков
    }

    public List<PlayersDTO> showAllPlayers() {
        List<Players> players = playersRepository.findAll();
        return playersMapper.mapToDto(players);
        //TODO: сделать экономичный поиск всех игроков
    }

    public Players findPlayerByName(String name) {
        return playersRepository.findByName(name);
    }

}
