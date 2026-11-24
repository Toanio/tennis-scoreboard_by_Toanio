package com.example.tennis.scoreboard.match;

import com.example.tennis.scoreboard.match.dto.FinishedMatchDTO;
import com.example.tennis.scoreboard.match.dto.MatchInfoDTO;
import com.example.tennis.scoreboard.match.dto.MatchesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = ActivePlayerMapper.class)
public interface MatchMapper {
    @Mapping(source = "player1", target = "firstPlayer")
    @Mapping(source = "player2", target = "secondPlayer")
    @Mapping(source = "winnerName", target = "winnerName")
    MatchInfoDTO mapToMatchInfoDto(ActiveMatch activeMatch);

    @Mapping(source = "player1.name", target = "firstPlayerName")
    @Mapping(source = "player2.name", target = "secondPlayerName")
    @Mapping(source = "winner.name", target = "winnerName")
    FinishedMatchDTO mapToFinishMatchDto(Match match);

    List<FinishedMatchDTO> mapToFinishList (Page<Match> matches);

    default MatchesDTO mapToMatchesDto(Page<Match> matches) {
        List<FinishedMatchDTO> finishedMatches = mapToFinishList(matches);

        return new MatchesDTO(
                finishedMatches,
                matches.getNumber(),
                matches.getTotalPages());
    }
}
