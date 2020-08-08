package cl.joshone.restaurantapi.model.login;

public class User {
	
	private String user;
	private String token;
	public User() {
	}
	public User(String user, String token) {
		this.user = user;
		this.token = token;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "User [user=" + user + "]";
	}

}
