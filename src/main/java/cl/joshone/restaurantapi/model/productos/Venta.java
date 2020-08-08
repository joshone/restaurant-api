package cl.joshone.restaurantapi.model.productos;

import java.util.List;

public class Venta {

	private int tipoPago;
	private List<ProductoComprado> productosComprados; 
	
	public Venta() {
	}
	
	public Venta(int tipoPago,
			List<ProductoComprado> productosComprados) {
		this.tipoPago = tipoPago;
		this.productosComprados = productosComprados;
	}

	public int getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(int tipoPago) {
		this.tipoPago = tipoPago;
	}
	public List<ProductoComprado> getProductosComprados() {
		return productosComprados;
	}
	public void setProductosComprados(List<ProductoComprado> productosComprados) {
		this.productosComprados = productosComprados;
	}
}
