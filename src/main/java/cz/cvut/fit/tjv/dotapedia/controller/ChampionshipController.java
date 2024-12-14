package cz.cvut.fit.tjv.dotapedia.controller;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.service.ChampionshipService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
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
@OpenAPIDefinition(info =
@Info(
        title = "DotaPedia",
        version = "0.1",
        description = "DotaPedia API for public use :)",
        contact = @Contact(name = "Vladyslav Hiliaka", email = "hyliavla@cvut.cz")
)
)
@Getter
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/championship", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChampionshipController {
    private ChampionshipService championshipService;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "OK")
    @Operation(summary = "Returns all championships.")
    public Iterable<Championship> readAll() {
        return getChampionshipService().readAll();
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "OK")
    @Operation(summary = "Create new championship.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Championship object that needs to be created."))
    public Championship create(@RequestBody Championship championship) {
        return getChampionshipService().create(championship);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Championship does not exist", content = @Content),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @Operation(summary = "Returns championship with given id.")
    @Parameter(description = "The id of a championship that needs to be retrieved.", required = true, name = "id")
    public Championship readById(@PathVariable("id") Long id) {
        Optional<Championship> championship = getChampionshipService().findById(id);
        if (championship.isPresent())
            return championship.get();
        else
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Championship does not exist", content = @Content),
            @ApiResponse(responseCode = "204", description = "OK")
    })
    @Operation(summary = "Update championship with given id.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Championship object that needs to be updated."
    ))
    @Parameter(description = "The id of a championship that needs to be updated.", required = true, name = "id")
    public void update(@PathVariable("id") Long id, @RequestBody Championship c) {
        c.setId(id);
        try {
            getChampionshipService().update(c);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Championship does not exist", content = @Content),
            @ApiResponse(responseCode = "204", description = "OK")
    })
    @Operation(summary = "Delete championship with given id.")
    @Parameter(description = "The id of a championship that needs to be deleted.", required = true, name = "id")
    public void delete(@PathVariable("id") Long id) {
        try {
            getChampionshipService().deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}/winner")
    @ApiResponses({
            @ApiResponse(responseCode = "422", description = "Provided championship or team does not exist.", content = @Content),
            @ApiResponse(responseCode = "400", description = "The team did not participate in the tournament", content = @Content),
            @ApiResponse(responseCode = "204", description = "OK")
    })
    @Operation(summary = "Add winner to championship with provided id.")
    @Parameter(description = "ID of the championship to which you want to add the winner.", required = true, name = "id")
    @Parameter(description = "The id of a team that won championship.", required = true, name = "t")
    public void addWinner(@PathVariable("id") Long id, @RequestParam Long t) {
        try {
            getChampionshipService().addWinner(id, t);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException a) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
