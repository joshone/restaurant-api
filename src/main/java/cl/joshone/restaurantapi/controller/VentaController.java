package cl.joshone.restaurantapi.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import cl.joshone.restaurantapi.model.productos.ArrayOfProductos;
import cl.joshone.restaurantapi.model.productos.Producto;
import cl.joshone.restaurantapi.model.productos.Venta;
import cl.joshone.restaurantapi.model.productos.VentaResponse;
import cl.joshone.restaurantapi.service.RestaurantService;

@RestController
@RequestMapping("/producto")
public class VentaController {
	private Logger logger = LoggerFactory.getLogger(VentaController.class);
	
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> all(){
		Gson gson = new Gson();
		ArrayOfProductos productos = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource("productos.json").getFile()
					, StandardCharsets.UTF_8));
			productos = gson.fromJson(br, ArrayOfProductos.class);
			if(productos != null ) {
				productos.getProductos().stream().forEach(System.out::println);
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException...{}", e.getMessage());
		} catch (IOException e) {
			logger.error("IOException...{}", e.getMessage());
		}
		return new ResponseEntity<> (productos.getProductos(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/venta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VentaResponse> venta(@RequestBody Venta venta){
		
		return new ResponseEntity<> (restaurantService.crearVenta(venta), HttpStatus.OK);
	}

}
