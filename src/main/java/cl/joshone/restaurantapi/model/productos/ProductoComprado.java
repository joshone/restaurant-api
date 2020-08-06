package cl.joshone.restaurantapi.model.productos;

public class ProductoComprado {
	
	private long idProducto;
	private long valorUnidad;
	private long cantidad;
	
	public ProductoComprado() {
	}
	
	public ProductoComprado(long idProducto, long valorUnidad, long cantidad) {
		this.idProducto = idProducto;
		this.valorUnidad = valorUnidad;
		this.cantidad = cantidad;
	}

	public long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	public long getCantidad() {
		return cantidad;
	}
	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}
	public long getValorUnidad() {
		return valorUnidad;
	}
	public void setValorUnidad(long valorUnidad) {
		this.valorUnidad = valorUnidad;
	}
}
