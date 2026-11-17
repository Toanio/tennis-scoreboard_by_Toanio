package com.example.tennis.scoreboard.Players;

import com.example.tennis.scoreboard.Players.dto.PlayersDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayersMapper {
    PlayersDTO mapToDto(Players players);

    List<PlayersDTO> mapToDto(Page<Players> players);
}
