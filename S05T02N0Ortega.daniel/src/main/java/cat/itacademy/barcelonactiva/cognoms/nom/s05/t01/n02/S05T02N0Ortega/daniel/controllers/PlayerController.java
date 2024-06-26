package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services.PlayerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerServiceInterface playerService;

    @PostMapping("/addPlayers")
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO, Authentication authentication) {
        return new ResponseEntity<>(playerService.save(playerDTO, authentication), HttpStatus.CREATED);
    }

    @PutMapping("/players/update/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable long id, @RequestBody PlayerDTO playerDTO) {

        PlayerDTO playerToUpdate = playerService.update(id, playerDTO);
        return new ResponseEntity<>(playerToUpdate, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(Authentication authentication) {

        List<PlayerDTO> players = playerService.findAll();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    }