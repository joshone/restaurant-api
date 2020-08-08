package cl.joshone.restaurantapi.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cl.joshone.restaurantapi.config.Simulaciones;
import cl.joshone.restaurantapi.errors.UnauthorizedException;
import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.model.login.User;
import cl.joshone.restaurantapi.model.productos.ArrayOfProductos;
import cl.joshone.restaurantapi.model.productos.Producto;
import cl.joshone.restaurantapi.model.productos.Venta;
import cl.joshone.restaurantapi.model.productos.VentaResponse;
import cl.joshone.restaurantapi.model.productos.to.Venta_;
import cl.joshone.restaurantapi.security.BasicFunction;
import cl.joshone.restaurantapi.security.GenericUtils;
import cl.joshone.restaurantapi.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	private Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);
	
	@Value("${custom.basic.list}")
	private List<String> basicList;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	Queue queue;
	
	@Override
	public List<Producto> productos(String token) {
		ArrayOfProductos productos = null;
		Gson gson = new Gson();
		if(!GenericUtils.isValid(basicList, token)) {
			throw new UnauthorizedException("usuario no autorizado");
		}else {
			try {
				BufferedReader br = new BufferedReader(
						new FileReader(new ClassPathResource("productos.json").getFile(), StandardCharsets.UTF_8));
				productos = gson.fromJson(br, ArrayOfProductos.class);
				if (productos != null) {
					productos.getProductos().stream().forEach(System.out::println);
				}
			} catch (FileNotFoundException e) {
				logger.error("FileNotFoundException...{}", e.getMessage());
			} catch (IOException e) {
				logger.error("IOException...{}", e.getMessage());
			}
		}
		return productos.getProductos();
	}
	
	@Override
	public User login(Credential credential) {
		BasicFunction<String, Credential> basic = GenericUtils::encode;
		String token = basic.to(credential);
		boolean existInList = basicList.stream().filter(x -> x.equals(token)).findFirst().isPresent();
		
		if(!existInList) {
			throw new UnauthorizedException("usuario no encontrado");
		}
		return new User(credential.getUsername(), "Basic ".concat(token));
	}

	@Override
	public VentaResponse crearVenta(String token, Venta venta) {
		Gson gson = new Gson();
		if(!GenericUtils.isValid(basicList, token)) {
			throw new UnauthorizedException("usuario no autorizado");
		}
		BasicFunction<Venta_, Venta> basic = GenericUtils::saveVenta_;
		Venta_ v = basic.to(venta);
		
		VentaResponse ventaResponse = new VentaResponse(v.getId(), 
				v.getMonto(), 
				v.getTimestampOperacion(), 
				"venta ok");
		try {
			logger.info("enviando > {}", ventaResponse);
			jmsTemplate.convertAndSend(queue, gson.toJson(ventaResponse));
			
		}catch(Exception e) {
			Simulaciones.listaVentasNoEncoladas.add(ventaResponse);
			logger.error("cola no disponible...{}", e.getMessage());
		}
		
		return ventaResponse;
	}

	@Override
	public List<VentaResponse> listadoVentasDia(String token) {
		if(!GenericUtils.isValid(basicList, token)) {
			throw new UnauthorizedException("usuario no autorizado");
		}
		LocalDate today = LocalDate.now();
		Predicate <VentaResponse> p = (v) -> v.getTimeStampOperacion().toLocalDate().equals(today);
		return Simulaciones.listaVentasDesencoladas
				.stream()
				.filter(p)
				.collect(Collectors.toList());		
	}
}
