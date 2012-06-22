package paquete;

import java.awt.Point;
import java.util.Random;

import javax.swing.JFrame;

import paquete.Pecera;
import paquete.Pez;


public class PeceraTest {
	
	private static Pecera pecera;
	static Random generator = new Random();
	
	
	
	public static void main(String[] args) {
		//peceraConPecesNormales();
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
		pecera.agregarPez( new PezControl(new Point(0,0),15));
		pecera.agregarPez( new PezSinAI(new Point(100,50),7));
		crearPezComun(7);
		crearPezComun(8);
		crearPezComun(7);
	}
	
	
	public static void peceraConPecesNormales(){
		prepararVentanaConPecera();
		crearPezComun(8);
		crearPezComun(7);
		crearPezComun(6);
		crearPezComun(8);
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
	
	private static void crearPezComun(int velocidad){
		int x = generator.nextInt(pecera.getWidth()) - 80;
		int y = generator.nextInt(pecera.getHeight()) - 80;
		Point p = new Point(x, y);
		
		pecera.agregarPez( new Pez(p, velocidad));
	}
	
}
