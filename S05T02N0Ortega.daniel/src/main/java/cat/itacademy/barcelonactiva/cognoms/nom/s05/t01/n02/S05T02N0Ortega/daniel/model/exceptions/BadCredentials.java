package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions;


import org.springframework.security.core.AuthenticationException;

public class BadCredentials extends AuthenticationException {

    public BadCredentials(String message) {
        super(message);
    }
}