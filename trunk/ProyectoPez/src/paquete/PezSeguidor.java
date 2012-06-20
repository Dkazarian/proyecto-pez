package paquete;

import java.awt.Point;

public class PezSeguidor extends Pez {
	
	
	public PezSeguidor(Point posicionInicial, int velocidad) {
		//TODO: feo feo
		super(posicionInicial, velocidad);
		imagePath = "graficos/sprites-pez-azul.png"; 
		this.cargarImagenes();
	}
	
	
	@Override
	public void mover() {
		if(this.objetivo==null){
			buscarObjetivo();
		}
		if(this.objetivo != null ){
			
			if(this.estaCercaDe(this.objetivo.getPosicion())){
				
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
	
	
	public void seguirA(CosaDePecera objetivo){
		this.setObjetivo(objetivo);
	}

	
	
}
