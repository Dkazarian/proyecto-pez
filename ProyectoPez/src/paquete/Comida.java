package paquete;


import java.awt.Point;
import java.awt.image.BufferedImage;

import utils.ImageUtils;

public class Comida extends CosaDePecera {
	
	protected BufferedImage imagenes[];
 
	protected int velocidad;
	protected boolean cayendo;
	
	
	public Comida(Point posicion) {
		this.posicion = posicion;
		this.cargarImagenes();		
		this.cayendo = true;
	}

	private void cargarImagenes() {
		imagenes = ImageUtils.splitImage(ImageUtils.loadImage("graficos/comida.png"), 1, 2);
		
	}

	@Override
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
		
	}

	@Override
	public int getVelocidad() {
	
		return 10;
	}

	@Override
	public BufferedImage getImagen() {
	
		if(this.cayendo == false) return imagenes[1];
		
		return imagenes[0];
	}

	@Override
	public void mover() {
		if(this.cayendo){
			this.posicion.y+= this.getVelocidad();
			
			if(!pecera.estaDentroDeLosLimites(this)){
				this.posicion.y = pecera.getHeight()-this.getAlto();
				this.cayendo = false;
				
			}
			
		}
		
		
	}
	public boolean esComida(){
		return true;
	}


}
