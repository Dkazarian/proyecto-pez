package paquete;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class CosaDePecera {
	
	protected Point posicion;
	
	protected Pecera pecera;

	public abstract void setVelocidad(int velocidad);

	public abstract int getVelocidad();

	public abstract BufferedImage getImagen();

	public abstract void mover();

	/***********************
	 **     ACCESSORS     **
	 ***********************/
	



	public int getPosicionX() {
		
		return posicion.x;
	}

	public int getPosicionY() {
		return posicion.y;
	}

	public void setPecera(Pecera pecera) {
			this.pecera = pecera;
			
	}


	public int getLargo() {
		return this.getImagen().getWidth();
	}

	
	public int getAlto() {
		return this.getImagen().getHeight();
	}

	public Pecera getPecera() {
		return pecera;
	}

	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}

	
	

	
	public Point getPosicion() {
		
		return this.posicion;
	}



	
}
