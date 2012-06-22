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
	protected Point destino;
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
		
		
	}
	
	public void cargarImagenes(String path){
		
			imagenes = ImageUtils.splitImage(ImageUtils.loadImage(path), 1, 2);
					
	}
	

	
	 

	/***********************
	 **  COMPORTAMIENTO   **
	 ***********************/
	
	public void mover() {
		if(this.objetivo == null){
			if (this.destino == null){
				//no tenemos ni objetivo ni destino, ejecutamos AI
				this.executeAI();
			}else{
				if (this.estaEnElPunto(destino)){
					destino = null;
				}else{
					this.irHaciaElPunto(destino);
				}
			}
		}else{
			//tenemos objetivo, vamos hacia el
			this.evaluarSiSeguirAlObjetivo();
		}
	}
	
	
	
	
	private void executeAI(){
		CosaDePecera amigo = buscarPezParaSeguirlo();
		if(amigo == null){
			//no hay nadie cerca, entonces exploramos
			int x = generator.nextInt(pecera.getHeight()/2-this.getAlto());
			int y = generator.nextInt(pecera.getWidth()/2-this.getLargo());
			this.destino = new Point(x, y);
			
		}else{
			//encontramos un pez, vamos hacia el
			this.setObjetivo(amigo);
		}
	}
	
	
	private void evaluarSiSeguirAlObjetivo(){
		if(this.getObjetivo().esUnPez()){
			//si estabamos siguiendo a un pez, buscamos comida
			Comida obj = buscarComida();
			if (obj == null){
				//simplemente seguimos al pez
				this.acercarseALaCosa(this.getObjetivo());
			}else{
				this.setObjetivo(obj);
			}
		}else if (this.getObjetivo().esComida()){
			//vamos por la comida.
			Comida obj = (Comida)this.getObjetivo();
			this.irHaciaLaCosa(obj);
			if (this.estaEnElPunto(obj.getPosicion())){
				//llegamos a la comida!!
				//TODO la destruimos.
				pecera.destruirComida(obj);
			}
		}else{ //es null
			this.executeAI();
		}
	}
	
	
	private Comida buscarComida(){
		for (Comida comida : pecera.getComidas()){
			return comida;
		}
		return null;
	}
	
	private Pez buscarPezParaSeguirlo(){ //nice name ;)
		for(Pez pez: this.pecera.getPeces()){
			if(pez != this && !pez.estaSiguiendoA(this) && this.puedeVerA(pez) ){
				//si no es este mismo pez, no esta quieto, y no me esta siguiendo =>
				return pez;
			}
		}
		return null; //si no encontro ninguno nos da null..
	}
	
	private void acercarseALaCosa(CosaDePecera pez){
		if (this.estaLejosDe(pez)){
			this.irHaciaElPunto(pez.getPosicion());
		}
	}
	
	private boolean estaLejosDe(CosaDePecera cosa){
		return !this.estaEnElPunto(cosa.getPosicion());
	}
	
	
	/***********************
	 **     MOVIMIENTO    **
	 ***********************/
	
	protected void irHaciaLaCosa(CosaDePecera cosa){
		this.irHaciaElPunto(cosa.getPosicion());
	}
	
	
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
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	

	public CosaDePecera getObjetivo() {
		
		return objetivo;
	}

	public void setObjetivo(CosaDePecera objetivo) {
		this.objetivo = objetivo;
	}


	public int getVelocidad() {
		return (int)this.velocidad;
	}
	
	public BufferedImage getImagen() {
		
		return this.imagenes[this.direccion.ordinal()];
	}
	
	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
		
	}
	
	@Override
	public void setVelocidad(int velocidad) {
	 
		this.velocidad = velocidad;
	}

	@Override
	public boolean esUnPez(){
		return true;
	}
	
}
