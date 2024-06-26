package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.UserNotFound;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PlayerServiceInterface {

    List<PlayerDTO> findAll();

    PlayerDTO save(PlayerDTO player, Authentication authentication) throws UserNotFound;

    PlayerDTO update (long id, PlayerDTO playerDTO) throws PlayerNotFound;

    PlayerDTO getBestWinRate();
    PlayerDTO getWorstWinRate();
    List<PlayerDTO> findAllByWinrate();

}

