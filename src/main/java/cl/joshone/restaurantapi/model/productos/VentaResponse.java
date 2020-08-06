package cl.joshone.restaurantapi.model.productos;

import java.time.LocalDateTime;

public class VentaResponse {
	
	private long idVenta;
	private double monto;
	private LocalDateTime timeStampOperacion;
	private String mensaje;
	
	public VentaResponse() {
	}
	public VentaResponse(long idVenta, double monto, LocalDateTime timeStampOperacion, String mensaje) {
		this.idVenta = idVenta;
		this.monto = monto;
		this.timeStampOperacion = timeStampOperacion;
		this.mensaje = mensaje;
	}
	public long getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(long idVenta) {
		this.idVenta = idVenta;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public LocalDateTime getTimeStampOperacion() {
		return timeStampOperacion;
	}
	public void setTimeStampOperacion(LocalDateTime timeStampOperacion) {
		this.timeStampOperacion = timeStampOperacion;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	
}
