package paquete;

import java.awt.Point;

public class PezPablo extends PezSinAI {

	
		public PezPablo(Point posicionInicial, int velocidad) {
			super(posicionInicial, velocidad);
		
			
		}
		@Override
		public void mover() {
			if(this.objetivo==null){
				buscarObjetivo();
			}
			
			if(this.objetivo != null){
				
				if(this.estaCercaDe(this.objetivo.getPosicion())){
					
					this.setVelocidad(objetivo.getVelocidad());
					
				}
				
				this.moverHacia(objetivo.getPosicion());
			}else super.mover();
			
			
		}
		
		
		private void buscarObjetivo() {
			for(Pez pez: this.pecera.getPeces()){
				if(pez!=this && this.puedeVer(pez) && !pez.getObjetivo().equals(this)){
					this.objetivo = pez;
					break;
				}
			}
			
		}
		private boolean puedeVer(Pez pez) {
			if(Math.abs(this.getPosicionX()-pez.getPosicionX())< 100 && Math.abs(this.getPosicionY()-pez.getPosicionY())<50){
				if(this.direccion == Direccion.DERECHA && this.getPosicionX()<pez.getPosicionX())
					return true;
				else if(this.direccion==Direccion.IZQUIERDA) return true;
				
				
			}
			return false;
		}
		
		
		
		


}
