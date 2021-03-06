package cl.joshone.restaurantapi.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cl.joshone.restaurantapi.config.Simulaciones;
import cl.joshone.restaurantapi.errors.BadRequestException;
import cl.joshone.restaurantapi.errors.UnauthorizedException;
import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.model.login.User;
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
	
	@Value("${usar.activemq}")
	private boolean usarActiveMQ;

	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue queue;

	@Override
	public User login(Credential credential) {
		BasicFunction<String, Credential> basic = GenericUtils::encode;
		String token = basic.to(credential);
		boolean existInList = basicList.stream().filter(x -> x.equals(token)).findFirst().isPresent();

		if (!existInList) {
			throw new UnauthorizedException("usuario no encontrado");
		}
		return new User(credential.getUsername(), "Basic ".concat(token));
	}

	@Override
	public VentaResponse crearVenta(String token, Venta venta) {
		Gson gson = new Gson();
		if (!GenericUtils.isValid(basicList, token)) {
			throw new UnauthorizedException("usuario no autorizado");
		}
		BasicFunction<Venta_, Venta> basic = GenericUtils::saveVenta_;
		Venta_ v = null;
		try {
			v = basic.to(venta);
		} catch (Exception e) {
			throw new BadRequestException("No fue posible hacer la transacción");
		}

		VentaResponse ventaResponse = new VentaResponse(v.getId(), v.getMonto(), v.getTimestampOperacion(), "venta ok");
		try {
			//almacena el mensaje en activemq
			logger.info("enviando > {}", ventaResponse);
			jmsTemplate.convertAndSend(queue, gson.toJson(ventaResponse));

		} catch (Exception e) {
			//si no está conectado al activemq, lo almacenamos en una lista temporal para ser procesado en la clase schedule
			Simulaciones.listaVentasNoEncoladas.add(ventaResponse);
			logger.error("cola no disponible...{}", e.getMessage());
		}

		return ventaResponse;
	}

	@Override
	public List<VentaResponse> listadoVentasDia(String token) {
		if (!GenericUtils.isValid(basicList, token)) {
			throw new UnauthorizedException("usuario no autorizado");
		}
		LocalDate today = LocalDate.now();
		Predicate<VentaResponse> p = (v) -> v.getTimeStampOperacion().toLocalDate().equals(today);
		//si se desea usar activemq y obtener la lista de productos
		if(usarActiveMQ) 
			return Simulaciones.listaVentasDesencoladas.stream().filter(p).collect(Collectors.toList());
		else {
			//retorna la lista de ventas simuladas en base de datos convertidas al tipo de salida
			return Simulaciones.listaVentas
					.stream()
					.map(venta -> new VentaResponse(venta.getId(), venta.getMonto(), venta.getTimestampOperacion(), "venta ok"))
					.collect(Collectors.toList());
		}
			
	}
}
