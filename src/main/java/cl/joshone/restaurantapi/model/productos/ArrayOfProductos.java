package cl.joshone.restaurantapi.model.productos;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ArrayOfProductos {
	@SerializedName("productos")
	private List<Producto> productos;

	public ArrayOfProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
