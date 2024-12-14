package cz.cvut.fit.tjv.dotapedia.controller;

import cz.cvut.fit.tjv.dotapedia.domain.Player;
import cz.cvut.fit.tjv.dotapedia.repository.PlayerRepository;
import cz.cvut.fit.tjv.dotapedia.service.PlayerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PlayerControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private PlayerService playerService;

    @Test
    void create() throws Exception {
        var p = new Player();
        p.setId(5L);
        p.setNickname("testPlayer");
        p.setTeam(null);
        Mockito.when(playerService.create(p)).thenReturn(p);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/player")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "id": 400,
                                          "nickname": "testPlayer",
                                          "team": null\s
                                        }""")
                                .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname", Matchers.is("testPlayer")));
    }
}
