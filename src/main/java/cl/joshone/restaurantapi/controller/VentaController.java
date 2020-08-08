package cl.joshone.restaurantapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.joshone.restaurantapi.domain.EndPoint;
import cl.joshone.restaurantapi.errors.UnauthorizedException;
import cl.joshone.restaurantapi.model.productos.Producto;
import cl.joshone.restaurantapi.model.productos.Venta;
import cl.joshone.restaurantapi.model.productos.VentaResponse;
import cl.joshone.restaurantapi.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "ventas")
@RestController
@RequestMapping(EndPoint.VERSION_1 + "/producto")
public class VentaController {
	
	@Autowired
	private RestaurantService restaurantService;

	@ApiOperation(value = "get", notes = "lista productos en venta")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> all(@RequestHeader("Authorization") String token) {
		if(token == null || token.equals("")) {
			throw new UnauthorizedException("No tienes permiso para acceder");
		}
		
		return new ResponseEntity<>(restaurantService.productos(token), HttpStatus.OK);
	}

	@ApiOperation(value = "post", notes = "realiza venta")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"), 
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(value = "/venta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VentaResponse> venta(@RequestHeader("Authorization") String token, @RequestBody Venta venta) {
		if(token == null || token.equals("")) {
			throw new UnauthorizedException("No tienes permiso para acceder");
		}
		return new ResponseEntity<>(restaurantService.crearVenta(token, venta), HttpStatus.OK);
	}
	
	@ApiOperation(value = "post", notes = "realiza venta")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"), 
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/ventasDelDia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VentaResponse>> ventasDelDia(@RequestHeader("Authorization") String token) {
		if(token == null || token.equals("")) {
			throw new UnauthorizedException("No tienes permiso para acceder");
		}
		return new ResponseEntity<>(restaurantService.listadoVentasDia(token), HttpStatus.OK);
	}

}
