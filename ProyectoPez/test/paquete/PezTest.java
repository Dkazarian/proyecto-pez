package paquete;

import java.awt.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import paquete.Pecera;
import paquete.Pez;

public class PezTest {
	
	Pez pez = new Pez(new Point(0,0),1);
	@Before
	public void setUp() {
		pez.setPecera(new Pecera(800,400));
	}
	@Test
	public void moverDerecha() {
		int xi = pez.getPosicionX();
		pez.moverDerechaHaciaElPunto(pez.getPosicionX()+1);
		Assert.assertTrue(xi<pez.getPosicionX());
	}
	@Test
	public void moverIzquierda() {
		int xi = pez.getPosicionX();
		pez.moverIzquierdaHaciaElPunto(pez.getPosicionX()-1);
		Assert.assertTrue(xi>pez.getPosicionX());

	}
	
	
}
