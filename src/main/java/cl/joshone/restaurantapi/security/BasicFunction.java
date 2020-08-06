package cl.joshone.restaurantapi.security;

@FunctionalInterface
public interface BasicFunction <R, T> {

	public R to(T t);
}
