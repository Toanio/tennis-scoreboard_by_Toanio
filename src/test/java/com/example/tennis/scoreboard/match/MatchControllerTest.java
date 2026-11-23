package com.example.tennis.scoreboard.match;

import com.example.tennis.scoreboard.exception.MatchNotFoundException;
import com.example.tennis.scoreboard.exception.PlayersNotEqualsException;
import com.example.tennis.scoreboard.match.dto.MatchDTORequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
public class MatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;

    @Test
    public void shouldGet201AfterMatchCreate() throws Exception {
        String requestJson = """
                {
                    "firstPlayerName": "BAS",
                    "secondPlayerName" : "Gas"
                }
                """;

        UUID matchId = UUID.randomUUID();

        when(matchService.createMatch(any(MatchDTORequest.class))).thenReturn(matchId);


        mockMvc.perform(post("/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(matchId.toString()));

        verify(matchService).createMatch(any(MatchDTORequest.class));
    }

    @Test
    public void shouldGet409WhenPlayersNameEquals() throws Exception {
        String requestJson = """
                {
                    "firstPlayerName": "BAS",
                    "secondPlayerName" : "bas"
                }
                """;

        when(matchService.createMatch(any(MatchDTORequest.class)))
                .thenThrow(new PlayersNotEqualsException("Players name cannot be equals"));


        mockMvc.perform(post("/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());

        verify(matchService).createMatch(any(MatchDTORequest.class));
    }

    @Test
    public void shouldGet400WhenPlayersNameEmpty() throws Exception {
        String requestJson = """
                {
                    "firstPlayerName": "BAS",
                    "secondPlayerName" : ""
                }
                """;


        mockMvc.perform(post("/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name cannot be blank"));

        verifyNoInteractions(matchService);
    }

    @Test
    public void shouldGet400WhenJsonIsInvalid() throws Exception {
        String requestJson = """
                {
                    "firstPlayerName": "BA
                    "secondPlayerName" : "
                }
                """;


        mockMvc.perform(post("/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(matchService);
    }

    @Test
    public void shouldGet200WhenSetPoints() throws Exception {
        UUID uuid = UUID.fromString("fd31a478-8583-4042-b4ec-44f435a65a52");

        String requestJson = """
                {
                    "name": "Bas"
                }
                """;


        mockMvc.perform(post("/matches/{uuid}/point", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet400WhenUUIDIsInvalid() throws Exception {
        String uuid = "invalid uuid";

        String requestJson = """
                {
                    "name": "Bas"
                }
                """;


        mockMvc.perform(post("/matches/{uuid}/point", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid UUID"));

        verifyNoInteractions(matchService);
    }

    @Test
    public void shouldGet400WhenNameIsEmpty() throws Exception {
        UUID uuid = UUID.fromString("fd31a478-8583-4042-b4ec-44f435a65a52");

        String requestJson = """
                {
                    "name": " "
                }
                """;


        mockMvc.perform(post("/matches/{uuid}/point", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name cannot be blank"));

        verifyNoInteractions(matchService);
    }

    @Test
    public void shouldGet400WhenMatchNotFound() throws Exception {
        UUID uuid = UUID.fromString("fd31a478-8583-4042-b4ec-44f435a65a5a");

        String requestJson = """
                {
                    "name": "Bas"
                }
                """;

        when(matchService.setPoint(uuid, "Bas"))
                .thenThrow(new MatchNotFoundException("Match with this UUID not found"));


        mockMvc.perform(post("/matches/{uuid}/point", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Match with this UUID not found"));

        verify(matchService).setPoint(uuid, "Bas");
    }
}
