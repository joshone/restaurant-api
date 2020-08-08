package cl.joshone.restaurantapi.listener;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cl.joshone.restaurantapi.config.Simulaciones;
import cl.joshone.restaurantapi.model.productos.VentaResponse;

@Component
public class Consumer {
	private Logger logger = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(destination = "ventas.dia")
	public void consume(String data) {
		Gson gson = new Gson();
		VentaResponse ventaResponse = gson.fromJson(data, VentaResponse.class);
		logger.info("mensaje recibido < {} >", ventaResponse);
//		logger.info("mensaje recibido < {} >", data);
		Random r = new Random();
		int valorRandom = r.nextInt(2);
		System.out.println(valorRandom);
		Simulaciones.listaVentasDesencoladas.add(ventaResponse);
	}
	
}
