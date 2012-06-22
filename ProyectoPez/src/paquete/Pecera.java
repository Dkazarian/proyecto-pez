package paquete;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.util.HashSet;
import java.util.Set;



import javax.swing.JPanel;
import javax.swing.Timer;

import utils.ImageUtils;

public class Pecera extends JPanel implements ActionListener{
	
	
	private static final long serialVersionUID = 8605588196339457872L; //Quita el warning de "no tiene serialVersionUID" :P
	Set<Pez> peces = new HashSet<Pez>();
	Set<Comida> comidas = new HashSet<Comida>(); //Esto es temporal, despues hay q ver si se pueden meter todas las cosas en el mismo lugar(?)
	private BufferedImage fondo; 
	Timer timer;
	

	/***********************
	 **   INICIALIZACION  **
	 ***********************/	
	
	public Pecera(int width, int height) {
		
		this.cargarFondo("graficos/pecera.jpg", width, height);
		
		
		//Por alguna razon no deja que Pecera sea MouseListener así que tuve que crear una clase que lo sea
		this.addMouseListener(new DetectorDeClicks(this));
		
		//Ejecuta this.actionPerfomed() cada 80 milisecs
		timer = new Timer(80, this); 
		timer.start();   
	
		
	}
	

	public void cargarFondo(String path, int width, int height){
		
		
		fondo = ImageUtils.resize(ImageUtils.loadImage("graficos/pecera.jpg"),width, height);
		this.setSize(width, height);
				
		
	}



	/***********************
	 **      EVENTOS      **
	 ***********************/	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		for(Pez pez: this.peces) pez.mover();
		for(Comida comida:comidas)comida.mover();
		repaint(); //Llama a paint para actualizar lo que se muestra
	}
	


	/***********************
	 **      RESTO(?)     **
	 ***********************/	
	public void paint(Graphics g){
			
		super.paint(g);
		g.drawImage(this.fondo,0,0,null);
		for(Pez pez:peces) g.drawImage(pez.getImagen(),pez.getPosicionX(),pez.getPosicionY(),null);
		for(Comida comida:comidas) g.drawImage( comida.getImagen(), comida.getPosicionX(), comida.getPosicionY(),null);
				
	}
		
	public boolean estaDentroDeLosLimites(CosaDePecera cosa){
		
		 
		return
			cosa.getPosicionX()>=0 && cosa.getPosicionX()+cosa.getLargo()< this.getWidth() &&
			cosa.getPosicionY()>=0 && cosa.getPosicionY()+cosa.getAlto() <= this.getHeight();  
		
	}
	
	public void destruirComida(Comida comida){
		this.comidas.remove(comida);
		for (Pez pez : this.peces){
			if(pez.getObjetivo()==comida) pez.setObjetivo(null);
		}
	}

	
	/***********************
	 **     ACCESSORS     **
	 ***********************/
	
	public Set<Pez> getPeces() {
		return peces;
	}
	public Set<Comida> getComidas() {
		return comidas;
	}
	
	


	public void agregarPez(Pez pez) {
		
		peces.add(pez);
		pez.setPecera(this);
		
	}


	public void addComida(Comida comida) {
		comida.setPecera(this);
		comidas.add(comida);
		
	}


	


	
	
			
	
}

