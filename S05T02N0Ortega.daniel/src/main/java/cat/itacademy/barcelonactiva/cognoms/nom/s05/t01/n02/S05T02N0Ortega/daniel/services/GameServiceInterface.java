package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.GameDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface GameServiceInterface {

    List<GameDTO> findAll();
    GameDTO playGame(long playerID);


    List<GameDTO> getByPlayerId(long playerID);

    void deleteByPlayer(long playerID);
}
