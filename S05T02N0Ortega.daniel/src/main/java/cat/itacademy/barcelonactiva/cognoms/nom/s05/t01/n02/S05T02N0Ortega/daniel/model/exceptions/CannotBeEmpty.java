package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.exceptions;

import org.springframework.security.core.AuthenticationException;

public class CannotBeEmpty extends AuthenticationException {

    public CannotBeEmpty(String message) {
        super(message);
    }
}