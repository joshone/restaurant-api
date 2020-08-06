package cl.joshone.restaurantapi.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
	private final Logger logger = LoggerFactory.getLogger(UnauthorizedException.class);
	
	public UnauthorizedException(String message){
        super(message);
        logger.warn(message);
    }
}
