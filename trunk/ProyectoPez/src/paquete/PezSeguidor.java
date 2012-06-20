package paquete;

import java.awt.Point;

public class PezSeguidor extends Pez {
	
	//TODO velocidad

	Point destino = new Point(400, 100);
		
	public PezSeguidor(Point posicionInicial, int velocidad) {
		super(posicionInicial, velocidad);
	}
	
	@Override
	public void mover() {
		if(this.objetivo==null){
			buscarObjetivo();
		}
		if(this.objetivo != null){
			
			if(this.estaCercaDe(destino)){
				
				this.setVelocidad(objetivo.getVelocidad());
				
			}
			
			this.moverHacia(objetivo.getPosicion());
		}else super.mover();
		
		
	}
	private void buscarObjetivo() {
		for(Pez pez: this.pecera.getPeces()){
			if(pez != this && pez != this.getObjetivo()){
				this.objetivo = pez;
				break;
			}
		}
		
	}
	

	
}
