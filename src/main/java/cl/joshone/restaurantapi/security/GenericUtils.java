package cl.joshone.restaurantapi.security;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.joshone.restaurantapi.config.Simulaciones;
import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.model.productos.Venta;
import cl.joshone.restaurantapi.model.productos.to.Venta_;

public class GenericUtils {
	private static Logger logger = LoggerFactory.getLogger(GenericUtils.class);

	public static String encode(Credential credential) {
		
		String auth = credential.getUsername().concat(":").concat(credential.getPassword());
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		
		return new String(encodedAuth);
	}

	public static Credential decode(String token) {
		String temp = token.split(" ")[1];
		String basicFormat = new String (Base64.getDecoder().decode(temp));
		String [] cred = basicFormat.split(":");
		
		return new Credential(cred[0], cred[1]);
	}
	
	public static Venta_ saveVenta_(Venta venta) {
		long nextId = Simulaciones.listaVentas.stream().count();
		
		double totalMonto = venta.getProductosComprados()
				.stream()
				.map( p -> p.getValorUnidad() * p.getCantidad() )
				.reduce(Long::sum).get();
		Venta_ v = new Venta_(nextId+1, venta.getProductosComprados(), LocalDateTime.now() , totalMonto, venta.getTipoPago() );
		Simulaciones.listaVentas.add(v);
		logger.info("transaccion guardada - id...{}", v.getId());
		return v;
	}

}