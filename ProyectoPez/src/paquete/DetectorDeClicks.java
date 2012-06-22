package paquete;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DetectorDeClicks implements MouseListener {

	private Pecera pecera;

	public DetectorDeClicks(Pecera pecera) {
		this.pecera = pecera;
		pecera.requestFocus();
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		Comida comida = new Comida(e.getPoint());
		
		pecera.addComida(comida);

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// TODO Auto-generated method stub
	
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
