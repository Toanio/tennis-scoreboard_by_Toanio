package com.example.tennis.scoreboard.Matches;

import com.example.tennis.scoreboard.Matches.dto.ActivePlayerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActivePlayerMapper {

    @Mapping(target = "points", expression = "java(player.getPoints().getDisplayValue())")
    ActivePlayerDTO mapToDto(ActivePlayer player);
}
