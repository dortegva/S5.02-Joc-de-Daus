package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions;

public class UserAlreadyExists extends RuntimeException{

    public UserAlreadyExists(String message) {
        super(message);
    }
}