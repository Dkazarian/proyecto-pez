package paquete;

import java.awt.Point;

public class PezMaster extends Pez {

	Point destino = new Point(400, 100);
	CosaDePecera objetivo = null;
	
	
	public PezMaster(Point posicionInicial, int velocidad) {
		super(posicionInicial, velocidad);
		imagePath = "graficos/sprites-pez-negro.png";
		this.cargarImagenes();
		
	}
	
	
	
	
}
