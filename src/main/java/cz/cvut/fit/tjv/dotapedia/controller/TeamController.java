package cz.cvut.fit.tjv.dotapedia.controller;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.domain.Team;
import cz.cvut.fit.tjv.dotapedia.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin
@Getter
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/team", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {
    private TeamService teamService;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK")
    @Operation(summary = "Returns all teams.")
    public Iterable<Team> readAll() {
        return getTeamService().readAll();
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "OK")
    @Operation(summary = "Create new team.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Team object that needs to be created."))
    public Team create(@RequestBody Team t) {
        return getTeamService().create(t);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Team does not exist", content = @Content),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Returns team with given id.")
    @Parameter(description = "The id of a team that needs to be retrieved.", required = true, name = "id")
    public Team readById(@PathVariable("id") Long id) {
        Optional<Team> team = getTeamService().findById(id);
        if (team.isPresent())
            return team.get();
        else
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Team does not exist", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Update team with given id.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Team object that needs to be updated."
    ))
    @Parameter(description = "The id of a team that needs to be updated.", required = true, name = "id")

    public void update(@PathVariable("id") Long id, @RequestBody Team t) {
        t.setId(id);
        try {
            getTeamService().update(t);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Team does not exist", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Delete team with given id.")
    @Parameter(description = "The id of a team that needs to be deleted.", required = true, name = "id")
    public void delete(@PathVariable("id") Long id) {
        try {
            getTeamService().deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/business")
    @ApiResponse(responseCode = "200", description = "OK")
    @Operation(summary = "Return all teams that participated in all championships.")
    public Iterable<Team> businessOperation(){
        return getTeamService().businessOperation();
    }

    @GetMapping("/{id}/participation")
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Team does not exist", content = @Content),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Return all tournaments in which the team participated.")
    @Parameter(description = "ID of the team whose participation is to be returned.", required = true, name = "id")
    public Iterable<Championship> allParticipatedChampionships(@PathVariable("id") Long id) {
        Optional<Team> team = getTeamService().findById(id);
        if (team.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        return team.get().getParticipatedIn();
    }



    @PutMapping("/{id}/participation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Provided championship or team does not exist.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Provided team is already invited to this championship", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Add team to championship.")
    @Parameter(description = "ID of the championship to which the team should be added.", required = true, name = "c")
    @Parameter(description = "ID of the team that should be added to championship.", required = true, name = "id")
    public void addTeamToChampionship(@PathVariable("id") Long id, @RequestParam Long c) {
        try {
            getTeamService().addTeamToChampionship(id, c);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException a) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/participation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Provided championship or team does not exist.", content = @Content),
            @ApiResponse(responseCode = "400", description = "The team was not invited to this championship.", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Remove team from championship.")
    @Parameter(description = "ID of the championship from which the team should be removed.", required = true, name = "c")
    @Parameter(description = "ID of the team that should be removed from championship.", required = true, name = "id")
    public void removeTeamFromChampionship(@PathVariable("id") Long id, @RequestParam Long c) {
        try {
            getTeamService().removeTeamFromChampionship(id, c);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException a) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
