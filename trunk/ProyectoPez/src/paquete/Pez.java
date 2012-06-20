package paquete;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pez implements CosaDePecera{
	
	protected BufferedImage imagenes[] = new BufferedImage[2];
	
	protected Direccion direccion;
	protected Point posicion;
	
	
	protected double velocidad;
	protected Pecera pecera;
	protected CosaDePecera objetivo;
	
	
	protected enum Direccion{
		IZQUIERDA,
		DERECHA	
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
		
			BufferedImage spriteSheet = this.getSpriteSheet(path);
			
			this.cargarImagenIzqYDer(spriteSheet);
			
			
		
	}
	

	protected void cargarImagenIzqYDer(BufferedImage spriteSheet) {
		imagenes[Direccion.DERECHA.ordinal()] = spriteSheet.getSubimage(
		           0, //Inicio X
		           0, //Inicio Y
		           spriteSheet.getWidth()/2, //Longitud
		           spriteSheet.getHeight()
		        );

			imagenes[Direccion.IZQUIERDA.ordinal()] = spriteSheet.getSubimage(
					spriteSheet.getWidth()/2, 
			           0, 
			           spriteSheet.getWidth()/2, 
			           spriteSheet.getHeight()
			        );
		
		
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
	 **     MOVIMIENTO    **
	 ***********************/
	
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
	
	protected void moverHacia(Point destino) {
		if(destino.x > this.getPosicionX())
			this.moverDerecha();
		else if(destino.x < this.getPosicionX())
			this.moverIzquierda();
		
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
	
	
	


	/***********************
	 **     POSICION      **
	 ***********************/

	public boolean estaCercaDe(Point pos){
		return Math.abs(this.getPosicionX()-pos.x+this.getLargo())<30 && 
		Math.abs(this.getPosicionY()-pos.y)<50;
	}
	

	private boolean seSalioDelLimite() {
		if(this.pecera != null){
			return !this.pecera.estaDentroDeLosLimites(this);
		}
		return false;
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
