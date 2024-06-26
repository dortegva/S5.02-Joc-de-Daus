package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.domain.Role;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.domain.User;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.PlayerNameAlreadyExists;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PlayerService implements PlayerServiceInterface {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper playerModelMapper;

    @Override
    public PlayerDTO save(PlayerDTO player, Authentication authentication) throws UserNotFound {
        String userEmail = authentication.getName();
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFound("User not found"));

        Player newPlayer = getPlayerEntityFrom(player);
        newPlayer.setUserId(user.getUserId());
        newPlayer.setUser(user);


        String playerName = player.getPlayerName();
        if (user.getRole() != Role.ANONYMOUS && !playerName.isBlank() && playerRepository.findByPlayerName(playerName).isPresent()) {
            throw new PlayerNameAlreadyExists("Player name already in use");
        }

        newPlayer.setPlayerName(
                user.getRole() == Role.ANONYMOUS ? "Anonymous" + new Random().nextInt(9000) + 1000 :
                        playerName.isBlank() ? "Anonymous" : playerName
        );

        return getPlayerDTOFrom(playerRepository.save(newPlayer));
    }

    @Override
    public PlayerDTO update(long id, PlayerDTO playerDTO) throws PlayerNotFound {
        Player existingPlayer = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFound("Player was not found"));

        if (!existingPlayer.getPlayerName().equals(playerDTO.getPlayerName())) {
            if (playerDTO.getPlayerName().isBlank()){
                throw new RuntimeException("Write a Username");}
            if (playerRepository.findByPlayerName(playerDTO.getPlayerName()).isPresent()) {
                throw new PlayerNameAlreadyExists("Player name already in use");
            }
        }
        existingPlayer.setPlayerName(playerDTO.getPlayerName());
        playerRepository.save(existingPlayer);
        return getPlayerDTOFrom(existingPlayer);
    }

    @Override
    public List<PlayerDTO> findAll() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()){
            throw new PlayerNotFound("No players found in the system");
        }
        return players.stream().map(this::getPlayerDTOFrom).collect(Collectors.toList());
    }

    @Override
    public List<PlayerDTO> findAllByWinrate() {
        List<Player> players = playerRepository.findAllByOrderByWinRateDesc();
        return players.stream().map(this::getPlayerDTOFrom).collect(Collectors.toList());
    }

    @Override
    public PlayerDTO getBestWinRate() {

        Player player = playerRepository.findTopByOrderByWinRateDesc();
        if (player == null) throw new PlayerNotFound("No players found");
        return getPlayerDTOFrom(player);
    }

    @Override
    public PlayerDTO getWorstWinRate() {
        Player player = playerRepository.findTopByOrderByWinRateAsc();

        if (player == null) throw new PlayerNotFound("No players found");
        return getPlayerDTOFrom(player);
    }


    private PlayerDTO getPlayerDTOFrom(Player player) {
        return playerModelMapper.map(player, PlayerDTO.class);
    }

    private Player getPlayerEntityFrom(PlayerDTO playerDTO) {
        return playerModelMapper.map(playerDTO, Player.class);
    }

}