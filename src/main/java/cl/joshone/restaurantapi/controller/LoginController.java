package cl.joshone.restaurantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.joshone.restaurantapi.errors.UnauthorizedException;
import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.model.login.User;
import cl.joshone.restaurantapi.service.RestaurantService;

@RestController
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody Credential credential) throws UnauthorizedException {
		
		return new ResponseEntity<User>(restaurantService.login(credential), HttpStatus.OK);
	}

}
