package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services.PlayerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class PlayerRankingController {

    @Autowired
    PlayerServiceInterface playerService;

    @GetMapping("/winRateAverage")
    public ResponseEntity<?> getSuccessAveragePlayers (){
        List<PlayerDTO> players = playerService.findAllByWinrate();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/winner")
    public ResponseEntity<?> getWinner (){
        PlayerDTO winner = playerService.getBestWinRate();
        return new ResponseEntity<>(winner, HttpStatus.OK);
    }

    @GetMapping("/loser")
    public ResponseEntity<?> getLoser (){
        PlayerDTO loser = playerService.getWorstWinRate();
        return new ResponseEntity<>(loser, HttpStatus.OK);
    }

}