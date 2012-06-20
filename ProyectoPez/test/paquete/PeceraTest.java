package paquete;

import java.awt.Point;

import javax.swing.JFrame;

import paquete.Pecera;
import paquete.Pez;
import paquete.PezMaster;
import paquete.PezSeguidor;


public class PeceraTest {
	
	public static Pecera pecera;
	
	
		
	
	public static void main(String[] args) {
		peceraConPecesNormales();
	    //peceraConPezMaster();
	}
	
	
	
	/***********************
	 **       TESTS       **
	 ***********************/
	
	public static void peceraConPecesNormales(){
		prepararVentanaConPecera();
		pecera.agregarPez( new Pez(new Point(200,100),5));
		pecera.agregarPez( new Pez(new Point(300,150),8));
		pecera.agregarPez( new Pez(new Point(100,140),8));
		pecera.agregarPez( new Pez(new Point(2,300),7));
		pecera.agregarPez( new Pez(new Point(100,310),10));
		pecera.agregarPez( new Pez(new Point(100,310),10));
	
	
		
	}
	
	 
	public static void peceraConPezMaster(){
		prepararVentanaConPecera();
		pecera.agregarPez( new PezSeguidor(new Point(200,100),5));
		pecera.agregarPez( new PezSeguidor(new Point(300,150),8));
		pecera.agregarPez( new PezSeguidor(new Point(100,140),8));
		pecera.agregarPez( new PezSeguidor(new Point(2,300),7));
		pecera.agregarPez( new PezSeguidor(new Point(100,310),10));
		pecera.agregarPez( new PezMaster(new Point(100,310),10));
		
	}
	
	/***********************
	 **   INICIALIZACION  **
	 ***********************/


	public static void prepararVentanaConPecera(){
		JFrame j = new JFrame("Pecera");
		
		pecera = new Pecera();
		j.add(pecera);
		j.setSize(pecera.getWidth(), pecera.getHeight());
		j.setResizable(false);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setVisible(true);
	}
	
}
