package paquete;

import java.awt.Point;
import java.awt.image.BufferedImage;

import utils.ImageUtils;
import utils.RandomGenerator;

public class Pez extends CosaDePecera{
	
	protected BufferedImage imagenes[];
	
	protected Direccion direccion;
	protected CosaDePecera objetivo;
	protected double velocidad;
	private Point destino;
	RandomGenerator generator = new RandomGenerator(); //lo usamos para explorar la pecera de momento
	
	protected enum Direccion{
		DERECHA,
		IZQUIERDA
	}
	
	/***********************
	 **   INICIALIZACION  **
	 ***********************/
	
	public Pez(Point posicionInicial, int velocidad) {
		this(posicionInicial,velocidad, "graficos/sprites-pez-rojo.png");
	}
	public Pez(Point posicionInicial, int velocidad, String pathImagen) {
		this.posicion = posicionInicial;
		this.direccion = Direccion.DERECHA;
		this.cargarImagenes(pathImagen);
		this.setVelocidad(velocidad);
		this.setObjetivo(null);
		this.setDestino(null);
	}
	
	public void cargarImagenes(String path){
			imagenes = ImageUtils.splitImage(ImageUtils.loadImage(path), 1, 2);
	}
	

	
	 

	/***********************
	 **  COMPORTAMIENTO   **
	 ***********************/

	@Override
	public boolean esUnPez(){ return true; }
	
	
	//metodo de llamado publico, inicio de la IA:
	public void mover() {
		if (tengoObjetivo()){
			cumplirObjetivo();
		}else{
			buscarObjetivo();
		}
	}
	
	
	
	
	
	private void cumplirObjetivo(){
		
		if (miObjetivoEsExplorar()){
			if (this.estaEnElPunto(this.getDestino())){
				this.setDestino(null);
			}else{
				this.irHaciaElPunto(this.getDestino());
			}
			
			
		}else if (miObjetivoEsUnPez()){
			this.acercarseALaCosa(this.getObjetivo());
			
			
		}else if (miObjetivoEsComida()){
			Comida comida = (Comida) this.getObjetivo();
			this.irHaciaLaComida(comida);
			
			
		}
	}
	
	
	
	
	
	
	private void buscarObjetivo(){
		Pez amigo = buscarPezParaSeguirlo();
		if(amigo == null){
			//no hay nadie cerca, entonces exploramos
			this.setDestino(buscarPuntoParaExplorar());
		}else{
			//encontramos un pez, entonces lo seguimos
			this.setObjetivo(amigo);
		}
	}
	
	
	private Pez buscarPezParaSeguirlo(){
		for(Pez pez: this.pecera.getPeces()){
			if(pez != this && !pez.estaSiguiendoA(this) && this.puedeVerA(pez) ){
				//si no es este mismo pez, no esta quieto, y no me esta siguiendo =>
				return pez;
			}
		}
		return null; //si no encontro ninguno nos da null..
	}
	
	
	private Point buscarPuntoParaExplorar(){
		final int cercaDelBorde = 100;
		int x; int y;
		int xActual = this.getPosicion().x;
		
		y = this.getPosicion().y + generator.randomInt(-100, 100);
		if ( y < 0 ) y = 0;
		if ( y > pecera.getHeight() ) y = pecera.getHeight();
		
		if (this.getDireccion()==Direccion.DERECHA){
			if (xActual > pecera.getWidth() - cercaDelBorde){
				x = puntoXalaIzquierdaDe(xActual);
			}else{ x= puntoXalaDerechaDe(xActual); }
		}else{
			if (xActual < cercaDelBorde){
				x = puntoXalaDerechaDe(xActual);
			}else{ x= puntoXalaIzquierdaDe(xActual); }
		}
		
		Point ret = new Point(x, y);
		return ret;
	}
	
	private int puntoXalaDerechaDe(int xActual){
		return generator.randomInt(xActual, pecera.getWidth()-this.getLargo());
	}
	private int puntoXalaIzquierdaDe(int xActual){
		return generator.randomInt(0, xActual);
	}
	
	
	

	public void notificarQueLlegoComida(Comida comida){
		//llego comida, vamos hacia ella!!
		this.setObjetivo(comida);
		this.setDestino(null); //esto es para evitar lios cuando explora.
	}
	
	
	private Comida buscarComida(){
		for (Comida comida : pecera.getComidas()){
			return comida;
		}
		return null;
	}
	
	
	
	/***********************************
	 **     ANALISIS DE MOVIMIENTO    **
	 ***********************************/
	
	private void irHaciaLaComida(Comida comida){
		this.irHaciaLaCosa(comida);
		if (this.estaEnElPunto(comida.getPosicion())){
			//llegamos a la comida!!
			pecera.destruirComida(comida);
			
			//y ahora nos fijamos si hay mas comida =)
			Comida masComida = buscarComida();
			if (masComida == null)		{ this.setObjetivo(null);
							 	   }else{ this.setObjetivo(masComida); }
			
		}
	}
	
	private void acercarseALaCosa(CosaDePecera cosa){
		if (this.estaLejosDe(cosa)){
			this.irHaciaElPunto(cosa.getPosicion());
		}
	}
	
	protected void irHaciaLaCosa(CosaDePecera cosa){
		this.irHaciaElPunto(cosa.getPosicion());
	}
	
	private boolean estaLejosDe(CosaDePecera cosa){
		return !this.estaEnElPunto(cosa.getPosicion());
		/*double distancia;
		distancia = Math.abs(cosa.getPosicion().x - this.getPosicion().x);
		distancia+= Math.abs(cosa.getPosicion().y - this.getPosicion().y);
		return distancia > 40;*/
	}
	
	
	
	
	
	/***********************
	 **     MOVIMIENTO    **
	 ***********************/
	
	
	
	protected void irHaciaElPunto(Point _destino) {
		if(_destino.x > this.getPosicionX())
			this.moverDerechaHaciaElPunto(_destino.x);
		else if(_destino.x < this.getPosicionX())
			this.moverIzquierdaHaciaElPunto(_destino.x);
		
		if(_destino.y > this.getPosicionY())
			this.moverAbajoHaciaElPunto(_destino.y);
		
		if(_destino.y < this.getPosicionY())
			this.moverArribaHaciaElPunto(_destino.y);
	}
	
	
	
	private void moverArribaHaciaElPunto(int y) {
		int newY = this.posicion.y - this.getVelocidad();
		if (newY < y){ newY = y; }
		this.posicion = new Point(this.posicion.x, newY);
		
	}
	private void moverAbajoHaciaElPunto(int y) {
		int newY = this.posicion.y + this.getVelocidad();
		if (newY > y){ newY = y; }
		this.posicion = new Point(this.posicion.x, newY);
	}
	
	public void moverIzquierdaHaciaElPunto(int x) {
		this.direccion = Direccion.IZQUIERDA;
		int newX = this.posicion.x - this.getVelocidad();
		if (newX < x){ newX = x; }
		this.posicion = new Point(newX, this.posicion.y);
	}
	
	public void moverDerechaHaciaElPunto(int x) {
		this.direccion = Direccion.DERECHA;
		int newX = this.posicion.x + this.getVelocidad();
		if (newX > x){ newX = x; }
		this.posicion = new Point(newX, this.posicion.y);
	}
	
	
	/***********************
	 **     POSICION      **
	 ***********************/

	private boolean estaEnElPunto(Point destino){
		return (this.posicion.equals(destino));
	}
	
	protected boolean puedeVerA(CosaDePecera cosa) {
		return 
			this.estaMirandoHacia(cosa.getPosicion());
	}
	
	
	private boolean estaMirandoHacia(Point punto) {
		return ( (punto.x < this.getPosicionX() )&& this.direccion == Direccion.IZQUIERDA) || 
		( (punto.x > this.getPosicionX()) && this.direccion == Direccion.DERECHA); 
	}
	
	protected boolean estaSiguiendoA(CosaDePecera cosa){
       return this.objetivo == cosa;   
	}
	
	
	public void setDireccion(Direccion direccion) { this.direccion = direccion; }
	public Direccion getDireccion() { return direccion; }

	



	public int getVelocidad() { return (int)this.velocidad; }
	
	public BufferedImage getImagen() { return this.imagenes[this.direccion.ordinal()]; }
	
	public void setVelocidad(double velocidad) { this.velocidad = velocidad; }
	
	@Override
	public void setVelocidad(int velocidad) { this.velocidad = velocidad; }

	
	
	
	public CosaDePecera getObjetivo() { return objetivo; }
	public void setObjetivo(CosaDePecera objetivo) { this.objetivo = objetivo; }

	public Point getDestino() { return destino; }
	public void setDestino(Point destino) { this.destino = destino; }
	
	
	private boolean tengoObjetivo(){
		return (this.getObjetivo() != null) || (this.getDestino() != null);
	}
	
	private boolean miObjetivoEsUnPez(){
		return this.getObjetivo().esUnPez() == true;
	}
	
	private boolean miObjetivoEsComida(){
		return this.getObjetivo().esComida() == true;
	}
	
	private boolean miObjetivoEsExplorar(){
		return this.getDestino() != null;
	}
	
}
