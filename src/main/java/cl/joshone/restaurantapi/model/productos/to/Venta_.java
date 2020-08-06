package cl.joshone.restaurantapi.model.productos.to;

import java.time.LocalDateTime;
import java.util.List;

import cl.joshone.restaurantapi.model.productos.ProductoComprado;

//esta clase simula ser la que va a la base de datos
public class Venta_ {
	
	private long id;
	private List<ProductoComprado> productosComprados;
	private LocalDateTime timestampOperacion;
	private double monto;
	private int tipoPago;
	
	public Venta_() {
	}
	public Venta_(long id, List<ProductoComprado> productosComprados, LocalDateTime timestampOperacion, double monto,
			int tipoPago) {
		this.id = id;
		this.productosComprados = productosComprados;
		this.timestampOperacion = timestampOperacion;
		this.monto = monto;
		this.tipoPago = tipoPago;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<ProductoComprado> getProductosComprados() {
		return productosComprados;
	}
	public void setProductosComprados(List<ProductoComprado> productosComprados) {
		this.productosComprados = productosComprados;
	}
	public LocalDateTime getTimestampOperacion() {
		return timestampOperacion;
	}
	public void setTimestampOperacion(LocalDateTime timestampOperacion) {
		this.timestampOperacion = timestampOperacion;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public int getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(int tipoPago) {
		this.tipoPago = tipoPago;
	}

}
