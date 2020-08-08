package cl.joshone.restaurantapi.scheduled;

import java.util.ArrayList;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cl.joshone.restaurantapi.config.Simulaciones;

@Component
public class VentasScheduled {
	
	private Logger logger = LoggerFactory.getLogger(VentasScheduled.class);
	
	@Autowired
	private Queue queue;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/*
	 * este schedule revisa la lista de ventas que no se pudieron encolar periodicamente, hasta que se conecte al activemq 
	 * y encolarlo
	 * */
	@Scheduled(fixedRate = 15000)
	public void encolarLista() {
		if(!Simulaciones.listaVentasNoEncoladas.isEmpty()) {
			logger.info("lista ventas no encoladas - procesando");
			try {
				Gson gson = new Gson();
				Simulaciones.listaVentasNoEncoladas.stream()
				.peek(System.out::println)
				.forEach(v -> jmsTemplate.convertAndSend(queue, gson.toJson(v) ));
				Simulaciones.listaVentasNoEncoladas = new ArrayList<>();
			}catch(Exception e) {
				logger.error("re-procesando...{}", e.getMessage());
			}
			
		}else {
			logger.info("lista ventas vacias");
		}
	}
	

}
