package cl.joshone.restaurantapi.model.productos;

import com.google.gson.annotations.SerializedName;

public class Producto {
	
	@SerializedName("id")
	private long id;
	@SerializedName("nombre")
	private String nombre;
	@SerializedName("descripcion")
	private String descripcion;
	@SerializedName("tipo")
	private String tipo;
	@SerializedName("precio")
	private double precio;
		
	public Producto(long id, String nombre, String descripcion, String tipo, double precio) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.precio = precio;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", tipo=" + tipo
				+ ", precio=" + precio + "]";
	}
}
