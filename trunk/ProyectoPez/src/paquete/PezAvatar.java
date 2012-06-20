package paquete;

import java.awt.Point;
import java.awt.image.BufferedImage;

import paquete.Pez.Direccion;

public class PezAvatar extends Pez {

	public PezAvatar(Point posicionInicial, int velocidad) {
		super(posicionInicial, velocidad, "graficos/pez avatar.png");
		
	}
	
	protected void cargarImagenIzqYDer(BufferedImage spriteSheet) {
		
		imagenes[Direccion.DERECHA.ordinal()] = spriteSheet.getSubimage(
		           0, //Inicio X
		           0, //Inicio Y
		            97, //Longitud
		           121 //Altura
		        );

			imagenes[Direccion.IZQUIERDA.ordinal()] = spriteSheet.getSubimage(
			           97, 
			           0, 
			           96, 
			           121 
			        );
		
	}
	
}
