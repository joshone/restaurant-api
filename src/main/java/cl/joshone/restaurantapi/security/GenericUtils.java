package cl.joshone.restaurantapi.security;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.joshone.restaurantapi.config.Simulaciones;
import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.model.productos.Venta;
import cl.joshone.restaurantapi.model.productos.to.Venta_;

public class GenericUtils {
	private static Logger logger = LoggerFactory.getLogger(GenericUtils.class);

	/*
	 * metodo para encodear las credenciales a basic
	 * */
	public static String encode(Credential credential) {
		
		String auth = credential.getUsername().concat(":").concat(credential.getPassword());
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		
		return new String(encodedAuth);
	}

	/*
	 * metodo para decodear el token  
	 * */
	public static Credential decode(String token) {
		String temp = token.split(" ")[1];
		String basicFormat = new String (Base64.getDecoder().decode(temp));
		String [] cred = basicFormat.split(":");
		
		return new Credential(cred[0], cred[1]);
	}
	
	/**
	 * metodo para almacenar las ventas como parte de un functional interface 
	 **/
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
	
	/*
	 * metodo para validar si el token se encuentra en la lista, simula autenticación con alguna fuente de datos
	 * **/
	public static boolean isValid(List<String> tokensList, String token) {
		String t = token.split(" ")[1];
		
		return tokensList.stream()
				.filter(r -> {
					boolean f = r.equals(t);
					return f;
				})
				.peek(f -> logger.info(f))
				.findFirst()
				.isPresent(); 
	}

}
