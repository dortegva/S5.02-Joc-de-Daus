package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.GameNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService implements GameServiceInterface {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerEntityService;


    @Autowired
    ModelMapper gameModel;
    @Transactional
    @Override
    public GameDTO playGame(long playerID) {
        Player playerSelected = playerRepository.findById(playerID)
                .orElseThrow(() -> new EntityNotFoundException("The player not found"));

        Game newGame = new Game();
        newGame.play();

        playerSelected.addingGame(newGame);
        playerSelected.setWinRate(playerSelected.calculateSuccessRate());
        playerRepository.save(playerSelected);

        newGame.setPlayer(playerSelected);
        gameRepository.save(newGame);

        return getGameDTOFrom(newGame);
    }
    @Transactional
    @Override
    public List<GameDTO> getByPlayerId(long playerID) {
        playerRepository.findById(playerID).
                orElseThrow(() -> new PlayerNotFound("Player was not found."));
        List<Game> games = gameRepository.findByPlayer_PlayerID(playerID);
        if (games.isEmpty()) {
            throw new GameNotFound("Empty Game History.");
        }
        return gameRepository.findByPlayer_PlayerID(playerID).stream().
                map(this::getGameDTOFrom).collect(Collectors.toList());

    }
    @Transactional
    @Override
    public void deleteByPlayer(long playerID) {
        Player player = playerRepository.findById(playerID).orElseThrow(() -> new PlayerNotFound("Player not found."));

        List<Game> games = gameRepository.findByPlayer_PlayerID(playerID);
        if (games.isEmpty()) {
            throw new GameNotFound("Empty Game History.");
        }

        gameRepository.deleteAllByPlayer(player);
    }

    @Override
    public List<GameDTO> findAll() {
        List<Game> games = gameRepository.findAll();
        if (games.isEmpty()){
            throw new GameNotFound("No games found in the system");
        }
        return games.stream().map(this::getGameDTOFrom).collect(Collectors.toList());
    }

    private GameDTO getGameDTOFrom(Game game) {
        return gameModel.map(game, GameDTO.class);
    }

}
