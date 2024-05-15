package dbp.proyecto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Unique resource already exist")
public class UniqueResourceAlreadyExist extends RuntimeException{
    public UniqueResourceAlreadyExist(String message) {
        super(message);
    }
}