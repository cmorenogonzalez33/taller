package com.example.taller.bbdd;

import com.example.taller.model.Articulo;

public interface BaseDatosI {
	public Integer insertarArticulo(Articulo articulo);
	
	public Articulo findArticuloById(Integer identificador);
	
	public void iniciar();

}
