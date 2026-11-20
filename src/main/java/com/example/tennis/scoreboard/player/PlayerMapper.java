package com.example.tennis.scoreboard.player;

import com.example.tennis.scoreboard.player.dto.PlayersDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayersDTO mapToDto(Player player);

    List<PlayersDTO> mapToDto(Page<Player> players);
}
