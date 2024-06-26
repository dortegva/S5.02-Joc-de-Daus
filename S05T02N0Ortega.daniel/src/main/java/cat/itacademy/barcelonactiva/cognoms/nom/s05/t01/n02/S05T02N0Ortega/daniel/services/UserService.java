package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.domain.User;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.UserDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions.GameNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService implements UserServiceInterface{

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ModelMapper userModelMapper;

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new GameNotFound("No games found in the system");
        }
        return users.stream().map(this::getUserDTOFrom).collect(Collectors.toList());
    }

    private UserDTO getUserDTOFrom(User user) {
        return userModelMapper.map(user, UserDTO.class);
    }


}
