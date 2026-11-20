package com.example.tennis.scoreboard.Players;

import com.example.tennis.scoreboard.Players.dto.PlayersDTO;
import com.example.tennis.scoreboard.Players.dto.PlayersDTORequest;
import com.example.tennis.scoreboard.exceptions.MessageDto;
import com.example.tennis.scoreboard.exceptions.PlayerAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayersService {
    private final PlayersRepository playersRepository;
    private final PlayersMapper playersMapper;

    public PlayersService(PlayersRepository playersRepository, PlayersMapper playersMapper) {
        this.playersRepository = playersRepository;
        this.playersMapper = playersMapper;
    }

    @Transactional
    public MessageDto createPlayer(PlayersDTORequest request) {
        Players findPlayer = playersRepository.findByNameIgnoreCase(request.name());
        if (findPlayer != null) {
            throw new PlayerAlreadyExistsException("Players already exist");
        }
        playersRepository.save(new Players(request.name()));
        return new MessageDto("Player successfully created");
    }

    @Transactional(readOnly = true)
    public List<PlayersDTO> showAllPlayers(Pageable pageable) {
        Page<Players> players = playersRepository.findAll(pageable);
        return playersMapper.mapToDto(players);
    }

    public Players findPlayerByName(String name) {
        return playersRepository.findByNameIgnoreCase(name);
    }

}
