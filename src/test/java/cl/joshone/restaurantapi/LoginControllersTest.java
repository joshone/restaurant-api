package cl.joshone.restaurantapi;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import static org.mockito.Matchers.any;
import com.google.gson.Gson;

import cl.joshone.restaurantapi.controller.LoginController;
import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.model.login.User;
import cl.joshone.restaurantapi.service.RestaurantService;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllersTest {

	private MockMvc mockMvc;

	@InjectMocks
	private LoginController loginController;

	@Mock
	private RestaurantService restaurantServiceMock;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}

	@Test
	public void testLoginControllerPostLogin_nocred() throws Exception {
		User user = new User("jose", "Basic am9zZTphbGVqYW5kcm8=");
		when(restaurantServiceMock.login(any(Credential.class))).thenReturn(user);

		mockMvc.perform(post("/v1/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testLoginControllerPostLogin_ok() throws Exception {

		User user = new User("jose", "Basic am9zZTphbGVqYW5kcm8=");
		Credential credential = new Credential("jose", "alejandro");
		when(restaurantServiceMock.login(any(Credential.class))).thenReturn(user);

		Gson gson = new Gson();

		mockMvc.perform(post("/v1/login").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(credential)))
				.andDo(x -> {
					//verificar que esté trayendo datos
					System.out.println(gson.toJson(x.getResponse()));
				})
				.andExpect(status().isOk()).andExpect(jsonPath("$.user", Matchers.is("jose")))
				.andExpect(jsonPath("$.token", Matchers.is("Basic am9zZTphbGVqYW5kcm8=")));
	}

}
