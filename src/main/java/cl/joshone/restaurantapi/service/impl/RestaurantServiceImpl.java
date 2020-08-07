package cl.joshone.restaurantapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	
	@Value("${custom.basic.list}")
	private List<String> basicList;
	
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
	public VentaResponse crearVenta(Venta venta) {
		BasicFunction<Venta_, Venta> basic = GenericUtils::saveVenta_; 
		Venta_ v = basic.to(venta);
		
		return new VentaResponse(v.getId(), 
				v.getMonto(), 
				v.getTimestampOperacion(), 
				"venta ok");
	}

	@Override
	public void listadoVentasDia() {
		// TODO Auto-generated method stub
		
	}

}
