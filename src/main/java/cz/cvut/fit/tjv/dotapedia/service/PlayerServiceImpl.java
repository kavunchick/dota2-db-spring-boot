package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.domain.Player;
import cz.cvut.fit.tjv.dotapedia.domain.Team;
import cz.cvut.fit.tjv.dotapedia.repository.ChampionshipRepository;
import cz.cvut.fit.tjv.dotapedia.repository.PlayerRepository;
import cz.cvut.fit.tjv.dotapedia.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Service
@Transactional
public class PlayerServiceImpl extends CrudServiceImpl<Player, Long> implements PlayerService {
    private PlayerRepository playerRepository;
    @Getter
    private TeamRepository teamRepository;
    @Getter
    private ChampionshipRepository championshipRepository;

    public Iterable<Player> readAll() {
        return playerRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void addPlayerToTeam(Long teamId, Long playerId) throws EntityNotFoundException, IllegalArgumentException {
        if (!getRepository().existsById(playerId) || !getTeamRepository().existsById(teamId))
            throw new EntityNotFoundException("Provided player or team does not exist.");
        Player player = getRepository().findById(playerId).get();
        Team team = getTeamRepository().findById(teamId).get();

        if (team.getMembers().contains(player))
            throw new IllegalArgumentException("Provided player is already a member of this team.");

        team.getMembers().add(player);
        player.setTeam(team);
        getRepository().save(player);
        getTeamRepository().save(team);
    }

    @Override
    public void removePlayerFromTeam(Long playerId) throws IllegalArgumentException, EntityNotFoundException {
        if (!getRepository().existsById(playerId))
            throw new EntityNotFoundException("Provided player does not exist.");
        Player player = getRepository().findById(playerId).get();
        if (player.getTeam() == null)
            throw new IllegalArgumentException("Provided player is not a member of any team.");
        Team team = player.getTeam();
        team.getMembers().remove(player);
        player.setTeam(null);
        getRepository().save(player);
        getTeamRepository().save(team);
    }

    @Override
    public void standIn(Long origPlayer, Long player2) throws IllegalArgumentException, EntityNotFoundException {
        if (!getRepository().existsById(origPlayer) || !getRepository().existsById(player2))
            throw new EntityNotFoundException("Provided player does not exist.");
        Player originalPlayer = getRepository().findById(origPlayer).get();
        Player standIn = getRepository().findById(player2).get();
        Championship championship = getChampionshipRepository().findChampionshipByClosestEndDate();
        Team team = originalPlayer.getTeam();
        if (team == null)
            throw new IllegalArgumentException("Provided player does not have a team.");
        if (standIn.getTeam() != null) {
            if (championship.getParticipants().contains(standIn.getTeam()))
                throw new IllegalArgumentException("The StandIn player is already participating in the upcoming tournament.");
            removePlayerFromTeam(standIn.getId());
        }
        Date now = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (!championship.getStartingDate().after(now))
            throw new EntityNotFoundException("There is no upcoming championships.");
        removePlayerFromTeam(originalPlayer.getId());
        addPlayerToTeam(team.getId(), standIn.getId());
        if (!championship.getParticipants().contains(team)) {
            championship.getParticipants().add(team);
            team.getParticipatedIn().add(championship);
        }
        getChampionshipRepository().save(championship);
        getRepository().save(originalPlayer);
        getRepository().save(standIn);
        getTeamRepository().save(team);
    }


    @Override
    protected CrudRepository<Player, Long> getRepository() {
        return playerRepository;
    }
}
