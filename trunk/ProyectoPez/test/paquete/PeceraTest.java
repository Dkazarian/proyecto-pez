package paquete;

import java.awt.Point;

import javax.swing.JFrame;

import paquete.Pecera;
import paquete.Pez;


public class PeceraTest {
	
	public static Pecera pecera;
	
	
		
	
	public static void main(String[] args) {
	//	peceraConPecesNormales();
		//peceraConAlgunosPecesAvatar();
		peceraConPezRobot();
	}
	




	private static void peceraConAlgunosPecesAvatar() {
		prepararVentanaConPecera();
		pecera.agregarPez( new Pez(new Point(100,300),10));
		pecera.agregarPez( new PezAvatar(new Point(200,100),10));
		pecera.agregarPez( new Pez(new Point(300,150),8));
		pecera.agregarPez( new Pez(new Point(100,140),13));
		pecera.agregarPez( new PezAvatar(new Point(2,150),20));
		pecera.agregarPez( new Pez(new Point(2,300),20));
		pecera.agregarPez( new Pez(new Point(100,310),10));
		pecera.agregarPez( new Pez(new Point(200,205),15));
	
		
	}



	/***********************
	 **       TESTS       **
	 ***********************/
	private static void peceraConPezRobot() {
		prepararVentanaConPecera();
		pecera.agregarPez( new PezControl(new Point(0,0),30));
		pecera.agregarPez( new PezSinAI(new Point(100,50),10));
		pecera.agregarPez( new Pez(new Point(400,205),15));
	}


	public static void peceraConPecesNormales(){
		prepararVentanaConPecera();
		pecera.agregarPez( new Pez(new Point(200,100),5));
		pecera.agregarPez( new Pez(new Point(300,150),8));
		pecera.agregarPez( new Pez(new Point(100,140),8));
		pecera.agregarPez( new Pez(new Point(2,300),7));
		pecera.agregarPez( new Pez(new Point(100,310),10));
		pecera.agregarPez( new Pez(new Point(100,310),10));
	
	
		
	}
	
	/***********************
	 **   INICIALIZACION  **
	 ***********************/


	public static void prepararVentanaConPecera(){
		JFrame j = new JFrame("Pecera");
		
		pecera = new Pecera(800,400);
		j.add(pecera);
		j.setSize(pecera.getWidth(), pecera.getHeight());
		j.setResizable(false);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setVisible(true);
	}
	
}
