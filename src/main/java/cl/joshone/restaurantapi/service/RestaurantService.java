package cl.joshone.restaurantapi.service;

import java.util.List;

import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.model.login.User;
import cl.joshone.restaurantapi.model.productos.Venta;
import cl.joshone.restaurantapi.model.productos.VentaResponse;

public interface RestaurantService {
	
	public User login(Credential credential);
	public VentaResponse crearVenta(Venta venta);
	public List<VentaResponse> listadoVentasDia();
	

}
