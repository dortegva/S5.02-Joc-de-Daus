package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto.UserDTO;

import java.util.List;

public interface UserServiceInterface {

    List<UserDTO> findAll();
}