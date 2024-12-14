package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.domain.Player;
import cz.cvut.fit.tjv.dotapedia.domain.Team;
import cz.cvut.fit.tjv.dotapedia.repository.ChampionshipRepository;
import cz.cvut.fit.tjv.dotapedia.repository.PlayerRepository;
import cz.cvut.fit.tjv.dotapedia.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PlayerServiceImplUnitTest {
    @MockBean
    private TeamRepository teamRepository;
    @MockBean
    private PlayerRepository playerRepository;
    @MockBean
    private ChampionshipRepository championshipRepository;

    Player player;
    Team team;
    Championship championship;
    PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
        teamRepository.deleteAll();
        championshipRepository.deleteAll();

        player = new Player();
        player.setId(1L);
        player.setNickname("testPlayer");

        team = new Team();
        team.setId(1L);
        team.setName("TestTeam");
        team.setWorldRanking(2);
        List<Player> list = new ArrayList();
        team.setMembers(list);
        championship = new Championship();

        playerRepository.save(player);
        teamRepository.save(team);

        playerService = new PlayerServiceImpl(playerRepository, teamRepository, championshipRepository);
    }

    @Test
    void addPlayerToTeam() {
        Mockito.when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
        Mockito.when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));
        Mockito.when(playerRepository.existsById(player.getId())).thenReturn(true);
        Mockito.when(teamRepository.existsById(team.getId())).thenReturn(true);
        playerService.addPlayerToTeam(team.getId(), player.getId());
        Assertions.assertSame(player.getTeam(), team);
        Mockito.verify(playerRepository, Mockito.atLeastOnce()).save(player);
        Mockito.verify(teamRepository, Mockito.atLeastOnce()).save(team);

    }

    @Test
    void removePlayerFromTeam() {
        player.setTeam(team);
        team.getMembers().add(player);
        Mockito.when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
        Mockito.when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));
        Mockito.when(playerRepository.existsById(player.getId())).thenReturn(true);
        Mockito.when(teamRepository.existsById(team.getId())).thenReturn(true);
        playerService.removePlayerFromTeam(player.getId());
        Assertions.assertFalse(team.getMembers().contains(player));
        Assertions.assertNull(player.getTeam());

    }
}
