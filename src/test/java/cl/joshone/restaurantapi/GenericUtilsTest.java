package cl.joshone.restaurantapi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.security.GenericUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class GenericUtilsTest {
	
	@Test
	public void testGenericUtils_encode() {
		Credential cred = new Credential("jose", "alejandro");
		String token = GenericUtils.encode(cred);
		assertEquals("Basic am9zZTphbGVqYW5kcm8=", "Basic ".concat(token));
	}
	
	public void testGenericUtils_decode() {
		String token = "Basic am9zZTphbGVqYW5kcm8=";
		Credential cred = GenericUtils.decode(token);
		assertEquals(cred, new Credential("jose", "alejandro"));
	}

}
