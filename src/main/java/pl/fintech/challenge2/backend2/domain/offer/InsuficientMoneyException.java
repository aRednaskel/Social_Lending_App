package pl.fintech.challenge2.backend2.domain.offer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsuficientMoneyException extends RuntimeException{
    public InsuficientMoneyException(String message) {
        super(message);
    }
}
