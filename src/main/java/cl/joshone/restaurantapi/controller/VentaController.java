package cl.joshone.restaurantapi.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.google.gson.Gson;

import cl.joshone.restaurantapi.domain.EndPoint;
import cl.joshone.restaurantapi.errors.UnauthorizedException;
import cl.joshone.restaurantapi.model.productos.ArrayOfProductos;
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
	private Logger logger = LoggerFactory.getLogger(VentaController.class);
	
	@Autowired
	private RestaurantService restaurantService;

	@ApiOperation(value = "get", notes = "lista productos en venta")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> all() {
		ArrayOfProductos productos = null;
		Gson gson = new Gson();

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream is = classLoader.getResourceAsStream("productos.json");
			productos = gson.fromJson(new InputStreamReader(is, "UTF-8"), ArrayOfProductos.class);
			if (productos != null) {
				productos.getProductos().stream().forEach(System.out::println);
			}
		} catch (IOException e) {
			logger.error("IOException...{}", e.getMessage());
		}
		return new ResponseEntity<>(productos.getProductos(), HttpStatus.OK);
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
