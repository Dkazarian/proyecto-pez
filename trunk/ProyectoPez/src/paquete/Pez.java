package paquete;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import utils.ImageUtils;

public class Pez implements CosaDePecera{
	
	protected BufferedImage imagenes[] = new BufferedImage[2];
	
	protected Direccion direccion;
	protected Point posicion;
	
	protected double velocidad;
	protected Pecera pecera;
	protected CosaDePecera objetivo;
	
	protected Point miDestino;
	protected double velocidadActual;
	protected boolean moviendome;
	Random generator = new Random(); //lo usamos para explorar la pecera de momento

	private int rangoVisionX = 200;

	private int rangoVisionY = 400;
	
	
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
		this.moviendome = false;
		
		
	}
	
	public void cargarImagenes(String path){
		
			imagenes = ImageUtils.splitImage(ImageUtils.loadImage(path), 1, 2);
					
	}
	

	
	protected BufferedImage getSpriteSheet(String path) {
		BufferedImage spriteSheet;
		try {
			spriteSheet = ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return spriteSheet;
	}

	

	/***********************
	 **        AI         **
	 ***********************/

	public void mover() {
		if(this.objetivo == null){
			if (this.miDestino == null){
				//no tenemos ni objetivo ni destino, ejecutamos AI
				this.executeAI();
			}else{
				//tenemos destino, vamos hacia el
				this.irHaciaElPunto(miDestino);
			}
		}else{
			//tenemos objetivo, vamos hacia el
			this.irHaciaLaCosa(this.objetivo);
		}
	}
	
	
	
	
	private void executeAI(){
		Pez amigo = buscarPezCercanoQueSeEsteMoviendo();
		if(amigo == null){
			//no hay nadie cerca, entonces exploramos
			int x = generator.nextInt(pecera.getHeight()/2-this.getAlto());
			int y = generator.nextInt(pecera.getWidth()/2-this.getLargo());
			this.miDestino = new Point(x, y);
			this.moviendome = true;
		}else{
			//encontramos un pez, vamos hacia el
			this.objetivo = amigo;
			this.moviendome = true;
		}
	}
	
	
	
	public boolean estaQuieto(){
		return this.moviendome == false;
	}
	
	private Pez buscarPezCercanoQueSeEsteMoviendo(){ //nice name ;)
		for(Pez pez: this.pecera.getPeces()){
			if(pez != this  && !pez.estaQuieto() && pez.objetivo != this ){
				//si no es este mismo pez, no esta quieto, y no me esta siguiendo =>
				return pez;
			}
		}
		return null; //si no encontro ninguno nos da null..
	}
	
	
	//TODO falta hacer.
	private boolean estaCercaDe(CosaDePecera cosa){
		int x = this.posicion.x - cosa.getPosicionX();
		int y = this.posicion.y - cosa.getPosicionY();
		return (-10<x)||(x<10);
		/*
		int vecX = Math.abs(this.posicion.x - cosa.getPosicionX());
		int vecY = Math.abs(this.posicion.y - cosa.getPosicionY());
		double modulo = Math.sqrt(vecX^2+vecY^2);
		return (modulo <= 10);*/
	}
	

	/***********************
	 **     MOVIMIENTO    **
	 ***********************/
	
	protected void irHaciaLaCosa(CosaDePecera _objetivo){
		if(this.estaEnElRangoDe(_objetivo)){
			this.moviendome = false;
			this.objetivo = null;
		}else{
			this.irHaciaElPunto(_objetivo.getPosicion());
		}
	}
	
	
	protected void irHaciaElPunto(Point destino) {
		Point oldPosition = new Point(this.getPosicionX(), this.getPosicionY());
		if (this.llegoAlPunto(destino)){
			this.moviendome = false; //llegamos, nos detenemos.
			this.miDestino = null;
			return;
		}
		
		
		if(destino.x > this.getPosicionX())
			this.moverDerechaHaciaElPunto(destino.x);
		else if(destino.x < this.getPosicionX())
			this.moverIzquierdaHaciaElPunto(destino.x);
		
		if(destino.y > this.getPosicionY())
			this.moverAbajoHaciaElPunto(destino.y);
		
		if(destino.y < this.getPosicionY())
			this.moverArribaHaciaElPunto(destino.y);
		
		/*if(destino.x > this.getPosicionX())
			this.irHaciaNuevaPosicionALaDerecha(destino.x);
		else if(destino.x < this.getPosicionX())
			this.irHaciaNuevaPosicionALaIzquierda(destino.x);
		
		if(destino.y > this.getPosicionY())
			this.irHaciaNuevaPosicionHaciaAbajo(destino.y);
		
		if(destino.y < this.getPosicionY())
			this.irHaciaNuevaPosicionHaciaArriba(destino.y);
		
		
		if (this.getPosicion().equals(oldPosition)){
			this.moviendome = false; //llegamos, nos detenemos.
			this.miDestino = null;
		}*/
	}
	
	
	
	
	
	
	
	/*protected void irHaciaNuevaPosicionALaDerecha(int x) {
		int newX = this.posicion.x + this.getVelocidad();
		if( this.posicion.x + this.getVelocidad() > x ) newX = x;
		if( puntoOcupado(new Point(newX, this.posicion.y))) { newX = this.posicion.x; }
		this.posicion.x = newX;
	}
	protected void irHaciaNuevaPosicionALaIzquierda(int x) {
		int newX = this.posicion.x - this.getVelocidad();
		if( this.posicion.x - this.getVelocidad() < x ) newX = x;
		if( puntoOcupado(new Point(newX, this.posicion.y))) { newX = this.posicion.x; }
		this.posicion.x = newX;
	}
	protected void irHaciaNuevaPosicionHaciaAbajo(int y) {
		int newY = this.posicion.y + this.getVelocidad();
		if( this.posicion.y + this.getVelocidad() > y ) newY = y;
		if( puntoOcupado(new Point(this.posicion.x, newY))) { newY = this.posicion.y; }
		this.posicion.y = newY;
	}
	protected void irHaciaNuevaPosicionHaciaArriba(int y) {
		int newY = this.posicion.y - this.getVelocidad();
		if( this.posicion.y - this.getVelocidad() < y ) newY = y;
		if( puntoOcupado(new Point(this.posicion.x, newY))) { newY = this.posicion.y; }
		this.posicion.y = newY;
	}*/
	
	
	
	
	
	
	
	
	private boolean llegoAlPunto(Point destino){
		return (this.posicion.equals(destino));
	}
	
	private void moverArribaHaciaElPunto(int y) {
		//TODO: mejorar
		int newY = this.posicion.y - this.getVelocidad();
		if (newY < y){ newY = y; }
		this.posicion = new Point(this.posicion.x, newY);
		
	}
	private void moverAbajoHaciaElPunto(int y) {
		//TODO: mejorar
		int newY = this.posicion.y + this.getVelocidad();
		if (newY > y){ newY = y; }
		this.posicion = new Point(this.posicion.x, newY);
	}
	
	public void moverIzquierdaHaciaElPunto(int x) {
		//TODO: mejorar
		this.direccion = Direccion.IZQUIERDA;
		int newX = this.posicion.x - this.getVelocidad();
		if (newX < x){ newX = x; }
		this.posicion = new Point(newX, this.posicion.y);
	}
	
	public void moverDerechaHaciaElPunto(int x) {
		//TODO: mejorar
		this.direccion = Direccion.DERECHA;
		int newX = this.posicion.x + this.getVelocidad();
		if (newX > x){ newX = x; }
		this.posicion = new Point(newX, this.posicion.y);
	}
	
	
	
	
	
	
	
	
	/*private boolean puntoOcupado(Point punto){
		for(Pez pez : pecera.getPeces()){
			if(pez.ocupaElPunto(punto)) return true;
		}
		return false;
	}
	
	
	
	
	public boolean ocupaElPunto(Point punto){
		return (this.ocupaElPuntoX(punto.x) && this.ocupaElPuntoY(punto.y));
	}
	
	private boolean ocupaElPuntoX(int x){
		int Xmin = this.posicion.x;
		return ((Xmin <= x) && (Xmin + this.getLargo() >= x));
	}
	private boolean ocupaElPuntoY(int y){
		int Ymin = this.posicion.y;
		return ((Ymin <= y) && (Ymin + this.getAlto() >= y));
	}*/
	
	
	
	/***********************
	 **     POSICION      **
	 ***********************/

	public boolean estaCercaDe(Point pos){
		return Math.abs(this.getPosicionX()-pos.x+this.getLargo())<20 && 
		Math.abs(this.getPosicionY()-pos.y)<30;
	}
	
	
	/*private boolean seSalioDelLimite() {
		if(this.pecera != null){
			return !this.pecera.estaDentroDeLosLimites(this);
		}
		return false;
	}*/
	
	protected boolean puedeVerA(CosaDePecera cosa) {
		
		
		return 
			Math.abs(this.getPosicionX()-cosa.getPosicionX()+this.getLargo())<= this.rangoVisionX && 
			Math.abs(this.getPosicionY()-cosa.getPosicionY())<=rangoVisionY &&
			this.estaMirandoHacia(cosa.getPosicion());
	}

	
	private boolean estaMirandoHacia(Point punto) {
		return ( (punto.x < this.getPosicionX() )&& this.direccion == Direccion.DERECHA) || 
		( (punto.x > this.getPosicionX()) && this.direccion == Direccion.IZQUIERDA); 
	}
	
	protected boolean estaEnElRangoDe(CosaDePecera cosa){
		return (Math.abs(this.getPosicionX()-cosa.getPosicionX()+this.getLargo())<=100)
				&&
			   (Math.abs(this.getPosicionY()-cosa.getPosicionY()+this.getLargo())<=100);
	}
	
	/***********************
	 **     ACCESSORS     **
	 ***********************/
	
	public int getVelocidad(){
		return (int)this.velocidad;
	}

	public BufferedImage getImagen() {
			
		return this.imagenes[this.direccion.ordinal()];
	}
	
	public int getPosicionX() {
		
		return posicion.x;
	}
	
	public int getPosicionY() {
		return posicion.y;
	}
	
	public void setPecera(Pecera pecera) {
			this.pecera = pecera;
			
	}
	@Override
	public int getLargo() {
		return this.getImagen().getWidth();
	}
	
	@Override
	public int getAlto(){
		return this.getImagen().getHeight();
	}
	
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	

	public Pecera getPecera() {
		return pecera;
	}

	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	@Override
	public Point getPosicion() {
		
		return this.posicion;
	}

	public CosaDePecera getObjetivo() {
		
		return objetivo;
	}

	public void setObjetivo(CosaDePecera objetivo) {
		this.objetivo = objetivo;
	}


	
	
	
	
}
