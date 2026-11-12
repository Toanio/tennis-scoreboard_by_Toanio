package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.MatchInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ActivePlayerMapper.class)
public interface MatchMapper {
    @Mapping(source = "player1", target = "firstPlayer")
    @Mapping(source = "player2", target = "secondPlayer")
    MatchInfoDTO mapToMatchInfoDto(ActiveMatch activeMatch);
}
