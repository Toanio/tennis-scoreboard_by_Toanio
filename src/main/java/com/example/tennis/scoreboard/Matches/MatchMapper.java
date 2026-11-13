package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.FinishedMatchDTO;
import com.example.tennis.scoreboard.Matches.dto.MatchInfoDTO;
import com.example.tennis.scoreboard.Matches.dto.MatchesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = ActivePlayerMapper.class)
public interface MatchMapper {
    @Mapping(source = "player1", target = "firstPlayer")
    @Mapping(source = "player2", target = "secondPlayer")
    MatchInfoDTO mapToMatchInfoDto(ActiveMatch activeMatch);

    @Mapping(source = "player1.name", target = "firstPlayerName")
    @Mapping(source = "player2.name", target = "secondPlayerName")
    @Mapping(source = "winner.name", target = "winnerName")
    FinishedMatchDTO mapToFinishMatchDto(Matches matches);

    List<FinishedMatchDTO> mapToFinishList (Page<Matches> matches);

    default MatchesDTO mapToMatchesDto(Page<Matches> matches) {
        List<FinishedMatchDTO> finishedMatches = mapToFinishList(matches);

        return new MatchesDTO(
                finishedMatches,
                matches.getNumber(),
                matches.getTotalPages());
    }

    //List<MatchesDTO> mapToMatchesDto(List<FinishedMatchDTO> matches);
}
