package com.example.taller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taller.bbdd.BaseDatosI;
import com.example.taller.model.Articulo;
import com.example.taller.services.CarritoCompraServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CarritoCompraServiceImplTest {
	
	@InjectMocks
	private CarritoCompraServiceImpl carritoService;
	
	@Mock
	private BaseDatosI baseDatos;
	
	@Test
	void testLimpiarCesta() {
		//Probamos que la cesta está vacía.
		assertTrue(carritoService.getArticulos().isEmpty());
		
		//Meto un artículo
		carritoService.addArticulo(new Articulo("Camiseta", 18.99));
		
		//Compruebo que no está limpia
		assertFalse(carritoService.getArticulos().isEmpty());
		
		//Llamar al método limpiar cesta
		carritoService.limpiarCesta();
		
		//Compruebo que está vacia
		assertTrue(carritoService.getArticulos().isEmpty());
	}
	
	@Test 
	void testAddArticulo() {
		carritoService.addArticulo(new Articulo("Articulo 1", 18.99));
		
		//Compruebo que la cesta no está vacía
		assertFalse(carritoService.getArticulos().isEmpty());
	}
	
	@Test
	void testGetNumArticulos() {
		carritoService.addArticulo(new Articulo("Articulo 2", 18.99));
		carritoService.addArticulo(new Articulo("Articulo 3", 18.99));
		Integer res = carritoService.getNumArticulo();
		assertEquals(2, res);
	}
	
	@Test
	void testGetArticulos() {
		//Añado varios artículos y luego compruebo su tamaño y comprobar uno de los artículos
		carritoService.addArticulo(new Articulo("Articulo 1", 18.99));
		carritoService.addArticulo(new Articulo("Articulo 2", 18.99));
		
		List<Articulo> res = carritoService.getArticulos();
		assertEquals(carritoService.getNumArticulo(), 2);
		
		assertEquals("Camiseta", res.get(0).getNombre());
		assertEquals(2, res.size());
	}
	
	@Test
	void testTotalPrice() {
		carritoService.addArticulo(new Articulo("Camiseta", 18.00));
		carritoService.addArticulo(new Articulo("Pantalón", 18.00));
		Double res = carritoService.totalPrice();
		assertEquals(38D, res);
	}
	
	@Test
	void testCalculadorDescuento() {
		Double descuento = carritoService.calculadorDescuento(100D, 10D);
		assertEquals(10D, descuento);
		assertNotEquals(90D, descuento);
	}
	
	@Test
	void testAplicarDescuento() {
		Articulo articulo = new Articulo("Camiseta", 20.00);
		when(baseDatos.findArticuloById(any(Integer.class))).thenReturn(articulo);
		Double res = carritoService.aplicarDescuento(1, 18D);
		assertEquals(2D, res);
		verify(baseDatos).findArticuloById(any(Integer.class));
		//Se llama dos veces
		//verify(baseDatos, times(2)).findArticuloById(1);
	}
	
	@Test
	void testInsertar() {
		Articulo articulo = new Articulo("Camiseta", 20.00);
		when(baseDatos.insertarArticulo(any(Articulo.class))).thenReturn(0);
		Integer identificador = carritoService.insertar(articulo);
		List<Articulo> articulos = carritoService.getArticulos();
		
		assertEquals(0, identificador);
		assertEquals("Camiseta", articulos.get(identificador).getNombre());
		assertEquals(28D, articulos.get(identificador).getPrecio());
		verify(baseDatos, atLeast(1)).insertarArticulo(any(Articulo.class));
	}
}
