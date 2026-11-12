package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.ActivePlayerDTO;
import com.example.tennis.scoreboard.Matches.dto.MatchDTO;
import com.example.tennis.scoreboard.Matches.dto.MatchDTORequest;
import com.example.tennis.scoreboard.Matches.dto.MatchInfoDTO;
import com.example.tennis.scoreboard.Players.Players;
import com.example.tennis.scoreboard.Players.PlayersService;
import org.springframework.stereotype.Service;

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
        Players player1 = playersService.findPlayerByName(request.firstPlayerName());
        Players player2 = playersService.findPlayerByName(request.secondPlayerName());

        UUID uuid = UUID.randomUUID();

        ActivePlayer activePlayer1 = new ActivePlayer(player1.getName());
        ActivePlayer activePlayer2 = new ActivePlayer(player2.getName());

        matchSession.put(uuid, new ActiveMatch(activePlayer1, activePlayer2));

        return uuid;
    }

    public MatchInfoDTO setPoint(UUID uuid, String name) {
        ActiveMatch match = matchSession.get(uuid);
        match.addPoint(name);
        saveFinishMatch(uuid);
        return matchMapper.mapToMatchInfoDto(match);
    }

    public void saveFinishMatch(UUID uuid) {
        ActiveMatch match = matchSession.get(uuid);
        if (match.winnerName != null) {
            Players players1 = playersService.findPlayerByName(match.getPlayer1().getName());
            Players players2 = playersService.findPlayerByName(match.getPlayer2().getName());
            Players winner = playersService.findPlayerByName(match.winnerName);
            matchesRepository.save(new Matches(players1, players2, winner));
            matchSession.remove(uuid);
        }
    }
}
