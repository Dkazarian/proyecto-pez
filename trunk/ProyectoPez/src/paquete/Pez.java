package paquete;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import utils.ImageUtils;

public class Pez extends CosaDePecera{
	
	protected BufferedImage imagenes[];
	
	protected Direccion direccion;
	protected CosaDePecera objetivo;
	protected double velocidad;
	private Point destino;
	//protected double velocidadActual;
	//protected boolean moviendome;
	Random generator = new Random(); //lo usamos para explorar la pecera de momento

	//private int rangoVisionX = 400;

	//private int rangoVisionY = 200;
	
	
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
			int x = generator.nextInt(pecera.getHeight()/2-this.getAlto());
			int y = generator.nextInt(pecera.getWidth()/2-this.getLargo());
			this.setDestino(new Point(x, y));
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
	

	public void notificarQueLlegoComida(Comida comida){
		//llego comida, vamos hacia ella!!
		this.setObjetivo(comida);
		this.setDestino(null); //esto es para evitar lios cuando explora.
	}
	
	

	/************************************
	 **     ANALISIS DE MOVIEMIENTO    **
	 ************************************/
	
	private void irHaciaLaComida(Comida comida){
		this.irHaciaLaCosa(comida);
		if (this.estaEnElPunto(comida.getPosicion())){
			//llegamos a la comida!!
			//TODO la destruimos.
			pecera.destruirComida(comida);
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
