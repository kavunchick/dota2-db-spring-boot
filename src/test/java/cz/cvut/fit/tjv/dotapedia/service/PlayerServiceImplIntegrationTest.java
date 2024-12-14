package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.domain.Player;
import cz.cvut.fit.tjv.dotapedia.domain.Team;
import cz.cvut.fit.tjv.dotapedia.repository.ChampionshipRepository;
import cz.cvut.fit.tjv.dotapedia.repository.PlayerRepository;
import cz.cvut.fit.tjv.dotapedia.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PlayerServiceImplIntegrationTest {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
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
        playerService.addPlayerToTeam(team.getId(), player.getId());
        Team teamFromRep = teamRepository.findById(team.getId()).get();
        Player playerFromRep = playerRepository.findById(player.getId()).get();
        Assertions.assertTrue(teamFromRep.getMembers().contains(playerFromRep));
        Assertions.assertEquals(playerFromRep.getTeam().getMembers(), teamFromRep.getMembers());
    }

    @Test
    void addPlayerToTeamNoPlayer() {
        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> playerService.addPlayerToTeam(team.getId(), 2L));
    }

    @Test
    void removePlayerFromTeam() {
        playerService.addPlayerToTeam(team.getId(), player.getId());
        Team teamFromRep = teamRepository.findById(team.getId()).get();
        Player playerFromRep = playerRepository.findById(player.getId()).get();
        playerService.removePlayerFromTeam(playerFromRep.getId());
        teamFromRep = teamRepository.findById(team.getId()).get();
        playerFromRep = playerRepository.findById(player.getId()).get();
        Assertions.assertFalse(teamFromRep.getMembers().contains(playerFromRep));
    }
}
