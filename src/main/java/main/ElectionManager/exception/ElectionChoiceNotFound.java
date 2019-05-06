package main.ElectionManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "choice was not found in database.")
public class ElectionChoiceNotFound extends RuntimeException {
}
