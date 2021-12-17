package com.example.taller.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taller.bbdd.BaseDatosImp;
import com.example.taller.model.Articulo;

@Service
public class CarritoCompraServiceImpl implements CarritoCompraServiceI {

	private List<Articulo> cesta = new ArrayList<>();
	
	@Autowired
	private BaseDatosImp bbdd = new BaseDatosImp();
	
	@Override
	public void limpiarCesta() {
		cesta.clear();
	}

	@Override
	public void addArticulo(Articulo articulo) {
		cesta.add(articulo);	
	}

	@Override
	public Integer getNumArticulo() {
		return cesta.size();
	}

	@Override
	public List<Articulo> getArticulos() {
		return cesta;
	}

	@Override
	public Double totalPrice() {
		Double total = 0D;
		
		for(Articulo articulo : cesta) {
			total = total + articulo.getPrecio();
		}
		return total;
	}

	@Override
	public Double calculadorDescuento(Double precio, Double porcentajeDescuento) {
		Articulo articulo = bbdd.findArticuloById(getNumArticulo());
		return precio -  precio*porcentajeDescuento/100;
	}
	
	@Override
	public Double aplicarDescuento(Integer idArticulo, Double porcentaje) {
		Double resultado = null;
		
		Articulo articulo = bbdd.findArticuloById(1);
		Articulo articulo2 = bbdd.findArticuloById(2);
		
		if(Optional.ofNullable(articulo).isPresent()) {
			resultado = calculadorDescuento(articulo.getPrecio(), porcentaje);
		} else {
			System.out.println("No se ha encontrado articulo con ID: " + idArticulo);
		}
		
		return resultado;
	}

	@Override
	public Integer insertar(Articulo articulo) {
		Integer identificador = bbdd.insertarArticulo(articulo);
		
		cesta.add(articulo);
		return identificador;
	}
}
