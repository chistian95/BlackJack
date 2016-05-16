package pantalla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Animacion {
	public static final int VELOCIDAD = 10;
	
	private int xInicio;
	private int yInicio;
	private int xFin;
	private int yFin;
	private int x;
	private int y;
	private Pantalla pt;
	private Timer t;
	
	public Animacion(int xInicio, int yInicio, int xFin, int yFin, Pantalla pt) {
		this.xInicio = xInicio;
		this.yInicio = yInicio;
		this.xFin = xFin;
		this.yFin = yFin;
		this.pt = pt;
		x = xInicio;
		y = yInicio;
		animacion();
	}
	
	private void animacion() {
		ActionListener listener = new ActionListener() {
			double deltaX = (xFin-xInicio)/VELOCIDAD;
			double deltaY = (yFin-yInicio)/VELOCIDAD;
			public void actionPerformed(ActionEvent e) {
				x += deltaX;
				y += deltaY;
				if(x <= xFin) {
					x = xFin;
				}
				if(yFin < yInicio && y <= yFin) {
					y = yFin;
				} else if(yFin >= yInicio && y >= yFin) {
					y = yFin;
				}
				if(x == xFin && y == yFin) {
					pararTimer();
				}
				pt.repaint();
			}
		};
		t = new Timer(1, listener);
		t.setRepeats(true);
		t.start();
	}
	
	private void pararTimer() {
		pt.quitarAnim(this);
		t.stop();		
	}

	public int getxInicio() {
		return xInicio;
	}

	public int getyInicio() {
		return yInicio;
	}

	public int getxFin() {
		return xFin;
	}

	public int getyFin() {
		return yFin;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}	
	
	public void setY(int y) {
		this.y = y;
	}
	
}
