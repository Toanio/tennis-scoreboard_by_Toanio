package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.*;
import com.example.tennis.scoreboard.Players.Players;
import com.example.tennis.scoreboard.Players.PlayersService;
import com.example.tennis.scoreboard.exceptions.MatchNotFoundException;
import com.example.tennis.scoreboard.exceptions.PlayerNotFoundException;
import com.example.tennis.scoreboard.exceptions.PlayersNotEqualsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchesService {
    private final PlayersService playersService;
    private final ConcurrentHashMap<UUID, ActiveMatch> matchSession = new ConcurrentHashMap<>();
    private final MatchMapper matchMapper;
    private final MatchesRepository matchesRepository;

    public MatchesService(PlayersService playersService, MatchMapper matchMapper, MatchesRepository matchesRepository) {
        this.playersService = playersService;
        this.matchMapper = matchMapper;
        this.matchesRepository = matchesRepository;
    }


    public UUID createMatch(MatchDTORequest request) {
        if (request.firstPlayerName().equals(request.secondPlayerName())) {
            throw new PlayersNotEqualsException("Players name cannot be equals");
        }
        Players player1 = playersService.findPlayerByName(request.firstPlayerName());
        Players player2 = playersService.findPlayerByName(request.secondPlayerName());

        if (player1 == null || player2 == null) {
            throw new PlayerNotFoundException("Player not found");
        }

        UUID uuid = UUID.randomUUID();

        ActivePlayer activePlayer1 = new ActivePlayer(player1.getName());
        ActivePlayer activePlayer2 = new ActivePlayer(player2.getName());

        matchSession.put(uuid, new ActiveMatch(activePlayer1, activePlayer2));

        return uuid;
    }

    public MatchInfoDTO setPoint(UUID uuid, String name) {
        Players player = playersService.findPlayerByName(name);
        if (player == null) {
            throw new PlayerNotFoundException("Player " + name + "not found");

        }
        ActiveMatch match = getActiveMatch(uuid);
        match.addPoint(player.getName());
        saveFinishMatch(uuid);
        return matchMapper.mapToMatchInfoDto(match);
    }

    @Transactional
    public void saveFinishMatch(UUID uuid) {
        ActiveMatch match = getActiveMatch(uuid);
        if (match.winnerName != null) {
            Players players1 = playersService.findPlayerByName(match.getPlayer1().getName());
            Players players2 = playersService.findPlayerByName(match.getPlayer2().getName());
            Players winner = playersService.findPlayerByName(match.winnerName);
            matchesRepository.save(new Matches(players1, players2, winner));
            matchSession.remove(uuid);
        }
    }

    @Transactional(readOnly = true)
    public MatchesDTO allFinishMatches(String playerName, Pageable pageable) {
        Page<Matches> matches;
        if (playerName != null) {
            matches = matchesRepository.findByPlayerName(playerName, pageable);
            return matchMapper.mapToMatchesDto(matches);
        }
        matches = matchesRepository.findAll(pageable);
        return matchMapper.mapToMatchesDto(matches);
    }

    public MatchInfoDTO getMatchScore(UUID uuid) {
        ActiveMatch match = getActiveMatch(uuid);
        return matchMapper.mapToMatchInfoDto(match);
    }

    private ActiveMatch getActiveMatch(UUID uuid) {
        ActiveMatch match = matchSession.get(uuid);
        if (match == null) {
            throw new MatchNotFoundException("Match with this UUID not found");
        }
        return match;
    }
}
