package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private GameService gameService;


    @GetMapping(path = "/allExistingGames")
    public ResponseEntity<List<GameDTO>> getAllGames() {

        List<GameDTO> games = gameService.findAll();
        return ResponseEntity.ok().body(games);
    }


    @GetMapping("/{playerId}/games")
    public ResponseEntity<?> getAllGames(@PathVariable("playerId") long playerId) {
        List<GameDTO> gameDTOList = gameService.getByPlayerId(playerId);
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }


    @PostMapping("/{playerId}/games")
    public ResponseEntity<?> playGame(@PathVariable("playerId") long playerId) {
        GameDTO gameDTO = gameService.playGame(playerId);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }


    @DeleteMapping("/{playerId}/games")
    public ResponseEntity<?> deleteAllGames(@PathVariable Long playerId) {
        gameService.deleteByPlayer(playerId);
        return new ResponseEntity<>("Games have been deleted.", HttpStatus.OK);
    }

}