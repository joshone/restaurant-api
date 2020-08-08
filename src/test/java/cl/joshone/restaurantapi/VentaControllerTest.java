package cl.joshone.restaurantapi;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import cl.joshone.restaurantapi.controller.VentaController;
import cl.joshone.restaurantapi.domain.EndPoint;
import cl.joshone.restaurantapi.model.productos.ProductoComprado;
import cl.joshone.restaurantapi.model.productos.Venta;
import cl.joshone.restaurantapi.model.productos.VentaResponse;
import cl.joshone.restaurantapi.service.RestaurantService;

@RunWith(SpringJUnit4ClassRunner.class)
public class VentaControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private VentaController ventaController;
	
	@Mock
	private RestaurantService restaurantServiceMock;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(ventaController).build();
	}

	@Test
	public void testVentaController_GetAll() throws Exception {
		
		mockMvc.perform(get(EndPoint.VERSION_1.concat("/producto/all"))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(10)));

	}
	
	@Test
	public void testVentaController_DoVenta() throws Exception {
		String auth = "Basic am9zZTphbGVqYW5kcm8=";
		List<ProductoComprado> list = new ArrayList<>();
		list.add( new ProductoComprado(1, 3500, 3));
		list.add( new ProductoComprado(2, 5000, 2));
		
		Venta venta = new Venta(0, list);
		VentaResponse ventaResponse = new VentaResponse(1, 10500.0, LocalDateTime.now(), "venta ok");
		Gson gson = new Gson();
		
		when(restaurantServiceMock.crearVenta(any(String.class), any(Venta.class))).thenReturn(ventaResponse);

		mockMvc.perform(post(EndPoint.VERSION_1.concat("/producto/venta"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(venta))
				.header("Authorization", auth))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idVenta", is(1)));

	}
	
	@Test
	public void testVentaController_DoVenta_noauth() throws Exception {
		List<ProductoComprado> list = new ArrayList<>();
		list.add( new ProductoComprado(1, 3500, 3));
		list.add( new ProductoComprado(2, 5000, 2));
		
		Venta venta = new Venta(0, list);
		VentaResponse ventaResponse = new VentaResponse(1, 10500.0, LocalDateTime.now(), "venta ok");
		Gson gson = new Gson();
		
		when(restaurantServiceMock.crearVenta(any(String.class), any(Venta.class))).thenReturn(ventaResponse);

		mockMvc.perform(post(EndPoint.VERSION_1.concat("/producto/venta"))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(venta)))
				.andExpect(status().is4xxClientError());
	}
	
	
	@Test
	public void testVentaController_ventasDelDia() throws Exception {
		String auth = "Basic am9zZTphbGVqYW5kcm8=";
		List<VentaResponse> list = new ArrayList<>();
		list.add( new VentaResponse(1, 10500.0, LocalDateTime.now(), "venta ok"));
		list.add( new VentaResponse(2, 35000.0, LocalDateTime.now(), "venta ok"));
		
				
		when(restaurantServiceMock.listadoVentasDia(any(String.class))).thenReturn(list);

		mockMvc.perform(get(EndPoint.VERSION_1.concat("/producto/ventasDelDia"))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", auth))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(2)));

	}
	
	@Test
	public void testVentaController_ventasDelDiaNoAuth() throws Exception {
		List<VentaResponse> list = new ArrayList<>();
		list.add( new VentaResponse(1, 10500.0, LocalDateTime.now(), "venta ok"));
		list.add( new VentaResponse(2, 35000.0, LocalDateTime.now(), "venta ok"));
		
				
		when(restaurantServiceMock.listadoVentasDia(any(String.class))).thenReturn(list);

		mockMvc.perform(get(EndPoint.VERSION_1.concat("/producto/ventasDelDia"))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());

	}

}
