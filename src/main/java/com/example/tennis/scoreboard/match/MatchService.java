package com.example.tennis.scoreboard.match;

import com.example.tennis.scoreboard.match.dto.*;
import com.example.tennis.scoreboard.player.Player;
import com.example.tennis.scoreboard.player.PlayerService;
import com.example.tennis.scoreboard.exception.MatchNotFoundException;
import com.example.tennis.scoreboard.exception.PlayerNotFoundException;
import com.example.tennis.scoreboard.exception.PlayersNotEqualsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchService {
    private final PlayerService playerService;
    private final ConcurrentHashMap<UUID, ActiveMatch> matchSession = new ConcurrentHashMap<>();
    private final MatchMapper matchMapper;
    private final MatchRepository matchRepository;

    public MatchService(PlayerService playerService, MatchMapper matchMapper, MatchRepository matchRepository) {
        this.playerService = playerService;
        this.matchMapper = matchMapper;
        this.matchRepository = matchRepository;
    }


    public UUID createMatch(MatchDTORequest request) {
        String firstName = request.firstPlayerName().trim();
        String secondName = request.secondPlayerName().trim();

        if (firstName.equals(secondName)) {
            throw new PlayersNotEqualsException("Players name cannot be equals");
        }
        Player player1 = playerService.findPlayerByName(request.firstPlayerName());
        Player player2 = playerService.findPlayerByName(request.secondPlayerName());

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
        Player player = playerService.findPlayerByName(name);
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
            Player player1 = playerService.findPlayerByName(match.getPlayer1().getName());
            Player player2 = playerService.findPlayerByName(match.getPlayer2().getName());
            Player winner = playerService.findPlayerByName(match.winnerName);
            matchRepository.save(new Match(player1, player2, winner));
            matchSession.remove(uuid);
        }
    }

    @Transactional(readOnly = true)
    public MatchesDTO allFinishMatches(String playerName, Pageable pageable) {
        Page<Match> matches;
        if (playerName != null) {
            matches = matchRepository.findByPlayerName(playerName, pageable);
            return matchMapper.mapToMatchesDto(matches);
        }
        matches = matchRepository.findAll(pageable);
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
