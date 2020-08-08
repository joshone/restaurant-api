package cl.joshone.restaurantapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cl.joshone.restaurantapi.controller.LoginController;
import cl.joshone.restaurantapi.controller.VentaController;
import cl.joshone.restaurantapi.domain.EndPoint;
import cl.joshone.restaurantapi.service.RestaurantService;

@RunWith(SpringJUnit4ClassRunner.class)
public class VentaControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private VentaController ventaController;
	
	@InjectMocks
	private LoginController loginController;
	
	@MockBean
	private RestaurantService restaurantServiceMock;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(ventaController).build();
	}

	@Test
	public void testVentaControllerGetAll() throws Exception {

		mockMvc.perform(get(EndPoint.VERSION_1.concat("/producto/all"))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(10)));

	}

}
