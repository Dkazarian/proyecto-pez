package paquete;

import java.awt.Point;

public class PezSinAI extends Pez{

	public PezSinAI(Point posicionInicial, int velocidad) {
		super(posicionInicial, velocidad, "graficos/pez-ftsm.png");
	}
	
	public PezSinAI(Point posicionInicial, int velocidad, String string) {
		super(posicionInicial, velocidad,string);
	}

	public void mover() {
		
		
		switch(this.direccion){
			
			case DERECHA: 
					this.moverDerecha();
					break;
			case IZQUIERDA:
					this.moverIzquierda();
					break;
				
		}
		
		
	}
	
	public void moverIzquierda() {
		
		this.posicion = new Point(this.posicion.x - this.getVelocidad(), this.posicion.y);
		
		if(this.direccion != Direccion.IZQUIERDA) this.girar();
		
		if(this.seSalioDelLimite()){
			
			this.girar();
			this.posicion.x = 0;
		}
		
		
	}
	

	public void moverDerecha() {
		
		this.posicion = new Point(this.posicion.x + this.getVelocidad(), this.posicion.y);
		
		if(this.direccion != Direccion.DERECHA) this.girar();
		
		if(this.seSalioDelLimite()){
			
			this.girar();
			this.posicion.x = this.pecera.getWidth()-this.getLargo();
			
		}
		
		
	}
	
	private boolean seSalioDelLimite() {
		if(this.pecera != null){
			return !this.pecera.estaDentroDeLosLimites(this);
		}
		return false;
	}
	
	private void girar() {
		
		switch(this.direccion){
		
			case DERECHA: 
					this.direccion = Direccion.IZQUIERDA;
					break;
			case IZQUIERDA:
					this.direccion = Direccion.DERECHA;
					break;
				
		}
		
		
		
	}
	
	protected void moverHacia(Point destino) {
		if(destino.x > this.getPosicionX())
			this.moverDerecha();
		else if(destino.x < this.getPosicionX())
			this.moverIzquierda();
		
		if(destino.y > this.getPosicionY())
			this.moverAbajo();
		
		if(destino.y < this.getPosicionY())
			this.moverArriba();
	}
	
	protected void moverArriba() {
		
		this.posicion.y -= this.getVelocidad()/2;
		if(this.seSalioDelLimite()) this.posicion.y = 0;
		
	}
	protected void moverAbajo() {
		//TODO: mejorar
		this.posicion.y+= this.getVelocidad()/2;
		if(this.seSalioDelLimite()) this.posicion.y = pecera.getHeight()-this.getAlto();
	}
	


}
