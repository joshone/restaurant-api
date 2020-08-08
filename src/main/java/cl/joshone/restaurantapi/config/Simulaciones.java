package cl.joshone.restaurantapi.config;

import java.util.ArrayList;
import java.util.List;

import cl.joshone.restaurantapi.model.productos.VentaResponse;
import cl.joshone.restaurantapi.model.productos.to.Venta_;

public class Simulaciones {
	
	/*
	 * listas que simulan usar una base de datos
	 * */
	public static List<Venta_> listaVentas = new ArrayList<>();
	public static List<VentaResponse> listaVentasNoEncoladas = new ArrayList<>();
	public static List<VentaResponse> listaVentasDesencoladas = new ArrayList<>();

}
