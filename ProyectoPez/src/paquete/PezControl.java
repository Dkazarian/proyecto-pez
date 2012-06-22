package paquete;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PezControl extends PezSinAI implements KeyListener {

	private boolean arriba;
	private boolean izquierda;
	private boolean derecha;
	private boolean abajo;
	
	public PezControl(Point posicionInicial, int velocidad) {
		super(posicionInicial, velocidad, "graficos/pez-robot.png" );
		
	}
	
	@Override
	public void setPecera(Pecera pecera) {
		super.setPecera(pecera);
		pecera.requestFocus();
		pecera.addKeyListener(this);
	}
	
	@Override
	public void mover() {
		if(arriba) this.moverArriba();
			else if(abajo) this.moverAbajo();
		if(derecha) this.moverDerecha(); 
			else if(izquierda)this.moverIzquierda();
	}

	// tecla sin pulsar
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                abajo = false;
                break;
            case KeyEvent.VK_UP:
                arriba = false;
                break;
            case KeyEvent.VK_LEFT:
                izquierda = false;
                break;
            case KeyEvent.VK_RIGHT:
                derecha = false;
                break;
        }
        
    }

    //tecla presionada
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                arriba = true;
                break;
            case KeyEvent.VK_LEFT:
              izquierda = true;
                break;
            case KeyEvent.VK_RIGHT:
                derecha = true;
                break;
            case KeyEvent.VK_DOWN:
                abajo = true;
                break;
        }
       
       } 
       
    @Override   
    public boolean estaQuieto(){
      return !(this.derecha | this.izquierda | this.arriba | this.abajo);
    }

    

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
