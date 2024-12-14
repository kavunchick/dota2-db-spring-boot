package cz.cvut.fit.tjv.dotapedia.controller;

import cz.cvut.fit.tjv.dotapedia.domain.Player;
import cz.cvut.fit.tjv.dotapedia.service.PlayerService;
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
@RequestMapping(value = "api/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
    private PlayerService playerService;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK")
    @Operation(summary = "Returns all players.")
    public Iterable<Player> readAll() {
        return getPlayerService().readAll();
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "OK")
    @Operation(summary = "Create new player.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Player object that needs to be created."))
    public Player create(@RequestBody Player p) {
        return getPlayerService().create(p);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Player does not exist", content = @Content),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Returns player with given id.")
    @Parameter(description = "The id of a player that needs to be retrieved.", required = true, name = "id")
    public Player readById(@PathVariable("id") Long id) {
        Optional<Player> player = getPlayerService().findById(id);
        if (player.isPresent())
            return player.get();
        else
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Player does not exist", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Update player with given id.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Player object that needs to be updated."
    ))
    @Parameter(description = "The id of a player that needs to be updated.", required = true, name = "id")
    public void update(@PathVariable("id") Long id, @RequestBody Player p) {
        p.setId(id);
        try {
            getPlayerService().update(p);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Provided player does not exist.", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Delete player with given id.")
    @Parameter(description = "The id of a player that needs to be deleted.", required = true, name = "id")
    public void delete(@PathVariable("id") Long id) {
        try {
            getPlayerService().deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}/team")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Provided player or team does not exist.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Provided player is already a member of this team.", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Add player to team")
    @Parameter(description = "The ID of a player that needs to be added to team.", required = true, name = "id")
    @Parameter(description = "The ID of the team to which the player should be added.", required = true, name = "t")
    public void addPlayerToTeam(@PathVariable("id") Long id, @RequestParam Long t) {
        try {
            getPlayerService().addPlayerToTeam(t, id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException a) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/team")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Provided player or team does not exist.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Provided player is not a member of provided team.", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Remove player from team")
    @Parameter(description = "The ID of a player that needs to be removed from team.", required = true, name = "id")
    public void removePlayerFromTeam(@PathVariable("id") Long id) {
        try {
            getPlayerService().removePlayerFromTeam(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException a) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/standIn")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Provided player does not exist or there is no upcoming championships.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Provided player does not have a team or StandIn player is already participating in the upcoming tournament.", content = @Content),
            @ApiResponse(responseCode = "204", content = @Content, description = "OK")
    })
    @Operation(summary = "Replace player with another one")
    @Parameter(description = "The ID of the player that needs to be replaced.", required = true, name = "p1")
    @Parameter(description = "The ID of the player to be replaced with.", required = true, name = "p1")
    public void standIn(@PathVariable("id") Long p1, @RequestParam Long p2) {
        try {
                getPlayerService().standIn(p1, p2);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException a) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
