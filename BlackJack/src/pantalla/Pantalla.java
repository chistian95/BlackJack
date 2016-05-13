package pantalla;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import carta.Carta;
import carta.Numero;
import carta.Palo;
import principal.Juego;

public class Pantalla extends JFrame implements MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private static final Font fuente = new Font("Arial", Font.BOLD, 14);
	
	private Juego jg;
	private List<Boton> botones;
	private BufferedImage bf;
	private boolean terminado;

	public Pantalla(Juego jg) {
		this.jg = jg;
		botones = new ArrayList<Boton>();
		terminado = false;
		
		setUndecorated(true);
        setSize(720, 480);
        setLocationRelativeTo(null);
        setVisible(true);
        
        addMouseListener(this);
        addKeyListener(this);
        
        bf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		dispose();
        		System.exit(0);
        	}
        });
	}
	
	public void paint(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		bff.setColor(new Color(0, 126, 0));
		bff.fillRect(0, 0, getWidth(), getHeight());
		bff.setFont(fuente);

		for(int i=0; i<jg.getMazo().size(); i++) {
			int x1 = getWidth()/2+48;
			int y1 = getHeight()/2-(2*i);
			bff.setColor(new Color(126, 0, 0));			
			bff.fillRect(x1, y1, 32, 48);
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
		
		for(int i=0; i<jg.getMano().getCartas().size(); i++) {
			Carta c = jg.getMano().getCartas().get(i);
			int x1 = getWidth()/2-128 + 34*i;
			int y1 = getHeight()/2 + 64;
			bff.setColor(Color.WHITE);
			bff.fillRect(x1, y1, 32, 48);
			
			bff.setColor(Color.BLACK);
			bff.drawString(Numero.NUMEROS[c.getNumero()], x1+12, y1+24);
			if(c.getPalo() >= 2) {
				bff.setColor(Color.RED);
			}
			bff.drawString(Palo.SIMBOLOS[c.getPalo()], x1+12, y1+34);
			
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
		
		for(int i=0; i<jg.getCasa().getCartas().size(); i++) {
			int x1 = getWidth()/2-128 + 34*i;
			int y1 = getHeight()/2-200 + 64;
			bff.setColor(new Color(126, 0, 0));
			bff.fillRect(x1, y1, 32, 48);
			
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
		
		bff.setColor(Color.WHITE);
		bff.setFont(fuente);
		bff.drawString(jg.getMano().getPuntos()+"", getWidth()/2 - 150, getHeight()/2+64);
				
		for(Boton boton : botones) {
			bff.setColor(new Color(0, 0, 126));
			if(terminado) {
				bff.setColor(new Color(64, 64, 64));
			}			
			bff.fillRect(boton.getX(), boton.getY(), boton.getDX(), boton.getDY());
			bff.setColor(Color.BLACK);
			bff.drawRect(boton.getX(), boton.getY(), boton.getDX(), boton.getDY());
			bff.setColor(Color.WHITE);
			bff.setFont(fuente);
			bff.drawString(boton.getAccion(), boton.getX()+16, boton.getY()+20);
		}
		
		if(terminado) {		
			turnoCasa();
			
			for(int i=0; i<jg.getCasa().getCartas().size(); i++) {
				Carta c = jg.getCasa().getCartas().get(i);
				int x1 = getWidth()/2-128 + 34*i;
				int y1 = getHeight()/2-200 + 64;
				bff.setColor(Color.WHITE);
				bff.fillRect(x1, y1, 32, 48);
				
				bff.setColor(Color.BLACK);
				bff.drawString(Numero.NUMEROS[c.getNumero()], x1+12, y1+24);
				if(c.getPalo() >= 2) {
					bff.setColor(Color.RED);
				}
				bff.drawString(Palo.SIMBOLOS[c.getPalo()], x1+12, y1+34);
				
				bff.setColor(Color.BLACK);
				bff.drawRect(x1, y1, 32, 48);
			}
			
			bff.setColor(Color.WHITE);
			bff.setFont(fuente);
			bff.drawString(jg.getCasa().getPuntos()+"", getWidth()/2 - 150, getHeight()/2-200+64);
			
			bff.setColor(Color.WHITE);
			bff.setFont(fuente);
			if(jg.getMano().getPuntos() > 21 || jg.getMano().getPuntos() < jg.getCasa().getPuntos()) {
				bff.drawString("Has perdido!", getWidth()/2-50, getHeight()/2-150);
			} else {
				bff.drawString("Has ganado!", getWidth()/2-50, getHeight()/2-150);
			}
		}
			
		g.drawImage(bf, 0, 0, null);
	}
	
	private boolean turnoCasa() {
		boolean res = false;
		if(jg.getCasa().getPuntos() < 16) {
			int rnd = (int) (Math.random()*jg.getMazo().size());
			Carta c = jg.getMazo().get(rnd);
			jg.getMazo().remove(rnd);
			jg.getCasa().getCartas().add(c);
			res = true;
		}		
		repaint();
		return res;
	}
	
	private void pulsarBoton(Boton b) {
		if(!terminado && b.getAccion().equals("REPARTIR")) {
			int rnd = (int) (Math.random()*jg.getMazo().size());
			Carta c = jg.getMazo().get(rnd);
			jg.getMazo().remove(rnd);
			jg.getMano().getCartas().add(c);
			if(jg.getMano().getPuntos() >= 21) {
				terminado = true;
			}			
		} else if(!terminado && b.getAccion().equals("PLANTARSE")) {
			terminado = true;
		}
		turnoCasa();		
	}
	
	public void meterBoton(Boton boton) {
		botones.add(boton);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(Boton boton : botones) {
			if(e.getX() >= boton.getX() && e.getX() <= boton.getX()+boton.getDX() && e.getY() >= boton.getY() && e.getY() <= boton.getY()+boton.getDY()) {
				pulsarBoton(boton);
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
