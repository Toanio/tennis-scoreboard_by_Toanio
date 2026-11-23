package com.example.tennis.scoreboard.player;

import com.example.tennis.scoreboard.exception.MessageDto;
import com.example.tennis.scoreboard.player.dto.PlayersDTO;
import com.example.tennis.scoreboard.player.dto.PlayersDTORequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @Test
    public void shouldGet201AfterCreatePlayer() throws Exception {
        String requestJson = """
                {
                  "name": "Toan"
                }
                """;
        when(playerService.createPlayer(any(PlayersDTORequest.class)))
                .thenReturn(new MessageDto("Player successfully created"));

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Player successfully created"));

        verify(playerService).createPlayer(any(PlayersDTORequest.class));
    }

    @Test
    public void shouldGet201WhenNameHasExactly2Characters() throws Exception {
        String requestJson = """
                {
                  "name": "To"
                }
                """;
        when(playerService.createPlayer(any(PlayersDTORequest.class)))
                .thenReturn(new MessageDto("Player successfully created"));

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Player successfully created"));

        verify(playerService).createPlayer(any(PlayersDTORequest.class));
    }

    @Test
    public void shouldGet201WhenNameHasExactly30Characters() throws Exception {
        String requestJson = """
                {
                  "name": "abcdefghijklmnopqrstuvwxyzabcd"
                }
                """;
        when(playerService.createPlayer(any(PlayersDTORequest.class)))
                .thenReturn(new MessageDto("Player successfully created"));

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Player successfully created"));

        verify(playerService).createPlayer(any(PlayersDTORequest.class));
    }

    @Test
    public void shouldGet400WhenNameContainsSpecialCharacters() throws Exception {
        String requestJson = """
                {
                  "name": "...."
                }
                """;

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(playerService);
    }

    @Test
    public void shouldGet400WhenNameExceeds30Characters() throws Exception {
        String requestJson = """
                {
                  "name": "qwertyuiopasdfghjklzxcvbnmqwertyuioppassdfgghj"
                }
                """;

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(playerService);
    }

    @Test
    public void shouldGet400WhenNameHasLessThan2Characters() throws Exception {
        String requestJson = """
                {
                  "name": "a"
                }
                """;

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(playerService);
    }

    @Test
    public void shouldGet400WhenNameIsNull() throws Exception {
        String requestJson = """
                {
                  "name": null
                }
                """;

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(playerService);
    }

    @Test
    public void shouldGet400WhenNameEmpty() throws Exception {
        String requestJson = """
                {
                  "name": ""
                }
                """;

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(playerService);
    }

    @Test
    public void shouldGet400WhenJsonIsInvalid() throws Exception {
        String requestJson = """
                {
                  "name": "
                }
                """;

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(playerService);
    }

    @Test
    public void shouldGet200AndPlayersListWhenShowAllPlayers() throws Exception {
        List<PlayersDTO> players = List.of(
                new PlayersDTO(1, "Toan")
        );

        when(playerService.showAllPlayers(any(Pageable.class))).thenReturn(players);

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Toan"));


        verify(playerService).showAllPlayers(any(Pageable.class));
    }

    @Test
    public void shouldGet200WhenPlayersListEmpty() throws Exception {
        List<PlayersDTO> players = List.of();

        when(playerService.showAllPlayers(any(Pageable.class))).thenReturn(players);

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));


        verify(playerService).showAllPlayers(any(Pageable.class));
    }


}
