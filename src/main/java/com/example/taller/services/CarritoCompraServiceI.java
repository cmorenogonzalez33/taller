package com.example.taller.services;

import java.util.List;

import com.example.taller.model.Articulo;

public interface CarritoCompraServiceI {
	public void limpiarCesta();
	
	public void addArticulo(Articulo articulo);
	
	public Integer getNumArticulo();
	
	public List<Articulo> getArticulos();
	
	public Double totalPrice();
	
	public Double calculadorDescuento(Double precio, Double porcetajeDescuento);
	
	public Double aplicarDescuento(Integer idArticulo, Double percentaje);
	
	public Integer insertar(Articulo articulo);
}
