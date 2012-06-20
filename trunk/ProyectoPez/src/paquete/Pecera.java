package paquete;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pecera extends JPanel implements ActionListener{
	
	
	private static final long serialVersionUID = 8605588196339457872L; //Quita el warning de "no tiene serialVersionUID" :P
	Set<Pez> peces = new HashSet<Pez>();
	private BufferedImage fondo; 
	Timer timer;
	

	/***********************
	 **   INICIALIZACION  **
	 ***********************/	
	
	public Pecera() {
				
		this.cargarFondo("graficos/pecera.jpg");
		
		//Ejecuta this.actionPerfomed() cada 80 milisecs
		timer = new Timer(80, this); 
		timer.start();   
	}
	

	void cargarFondo(String path){
		
		try{
			
			fondo = ImageIO.read(new File(path));
				
			this.setSize(fondo.getWidth(), fondo.getHeight());
				
			
		} catch (IOException e) {
			
			throw new RuntimeException("Problema cargando el fondo de la pecera: "+e.getMessage());
		}
	}



	/***********************
	 **       RESTO(?)    **
	 ***********************/	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		for(Pez pez: this.peces) pez.mover();
		repaint(); //Llama a paint para actualizar lo que se muestra
	}
		

	public void paint(Graphics g){
			
		super.paint(g);
		g.drawImage(this.fondo,0,0,null);
		for(Pez pez:peces) g.drawImage(pez.getImagen(),pez.getPosicionX(),pez.getPosicionY(),null);
				
	}
		
	public boolean estaDentroDeLosLimites(CosaDePecera cosa){
		
		 
		return
			cosa.getPosicionX()>=0 && cosa.getPosicionX()+cosa.getLargo()< this.getWidth() &&
			cosa.getPosicionY()>=0 && cosa.getPosicionY()+cosa.getAlto()< this.getHeight();  
		
	}

	
	/***********************
	 **     ACCESSORS     **
	 ***********************/
	
	public Set<Pez> getPeces() {
		return peces;
	}
	
	
	@Override
	public int getWidth() {
		
		return fondo.getWidth();
	}
	
	@Override
	public int getHeight() {
		
		return fondo.getHeight();
	}

	public void agregarPez(Pez pez) {
		
		peces.add(pez);
		pez.setPecera(this);
		
	}
	
	
			
	
}

