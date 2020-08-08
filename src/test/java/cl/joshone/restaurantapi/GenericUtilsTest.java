package cl.joshone.restaurantapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.joshone.restaurantapi.model.login.Credential;
import cl.joshone.restaurantapi.security.GenericUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {
	    "custom.basic.list=am9zZTphbGVqYW5kcm8=,cm9iZXJ0bzpjb3J0ZXM=,cGVkcm86cmFtaXJleg==,Y2FybWVuOm1hZ2FsbGFuZXM=,am9yZ2U6c2FsaW5hcw==,ZmVybmFuZG86Y29ydGVz",
	})

public class GenericUtilsTest {
	
	@Value("#{'${custom.basic.list}'.split(',')}")
	private List<String> basicList;
	
	@Test
	public void testGenericUtils_encode() {
		Credential cred = new Credential("jose", "alejandro");
		String token = GenericUtils.encode(cred);
		assertEquals("Basic am9zZTphbGVqYW5kcm8=", "Basic ".concat(token));
	}
	
	@Test
	public void testGenericUtils_decode_compareobject() {
		String token = "Basic am9zZTphbGVqYW5kcm8=";
		Credential cred = GenericUtils.decode(token);
		//comparar instancia contra otra instancia
		assertNotEquals(cred, new Credential("jose", "alejandro"));
	}
	
	@Test
	public void testGenericUtils_decode_comparetostring() {
		String token = "Basic am9zZTphbGVqYW5kcm8=";
		Credential cred = GenericUtils.decode(token);
		//comparar instancia contra otra instancia usando toString 
		assertEquals(cred.toString(), new Credential("jose", "alejandro").toString());
	}
	
	
	@Test
	public void testGenericUtils_isValid() {
		String token = "Basic am9zZTphbGVqYW5kcm8=";
		boolean t = GenericUtils.isValid(basicList, token);
		assertEquals(true, t);
	}

}
