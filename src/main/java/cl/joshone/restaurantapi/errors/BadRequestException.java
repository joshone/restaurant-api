package cl.joshone.restaurantapi.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private final Logger logger = LoggerFactory.getLogger(BadRequestException.class);
	
	public BadRequestException(String message){
        super(message);
        logger.warn(message);
    }
}
