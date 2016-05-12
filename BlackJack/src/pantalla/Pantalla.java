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
	private static final Font fuente = new Font("Verdana", Font.BOLD, 14);
	
	private Juego jg;
	private List<Boton> botones;
	private BufferedImage bf;

	public Pantalla(Juego jg) {
		this.jg = jg;
		botones = new ArrayList<Boton>();
		
		setUndecorated(true);
        setSize(1280, 720);
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

		for(int i=0; i<jg.getMazo().size(); i++) {
			int x1 = getWidth()/2-16;
			int y1 = getHeight()/2-(2*i);
			bff.setColor(new Color(126, 0, 0));			
			bff.fillRect(x1, y1, 32, 48);
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
		
		for(int i=0; i<jg.getMano().size(); i++) {
			Carta c = jg.getMano().get(i);
			int x1 = getWidth()/2-128 + 34*i;
			int y1 = getHeight()/2 + 64;
			bff.setColor(Color.WHITE);
			bff.fillRect(x1, y1, 32, 48);
			
			bff.setColor(Color.BLACK);
			bff.drawString(Numero.NUMEROS[c.getNumero()], x1+6, y1+24);
			if(c.getPalo() >= 2) {
				bff.setColor(Color.RED);
			}
			bff.drawString(Palo.SIMBOLOS[c.getPalo()], x1+16, y1+24);
			
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
				
		for(Boton boton : botones) {
			bff.setColor(new Color(0, 0, 126));
			bff.fillRect(boton.getX(), boton.getY(), boton.getDX(), boton.getDY());
			bff.setColor(Color.BLACK);
			bff.drawRect(boton.getX(), boton.getY(), boton.getDX(), boton.getDY());
			bff.setColor(Color.WHITE);
			bff.setFont(fuente);
			bff.drawString(boton.getAccion(), boton.getX()+16, boton.getY()+20);
		}
				
		g.drawImage(bf, 0, 0, null);
	}
	
	private void pulsarBoton(Boton b) {
		if(b.getAccion().equals("REPARTIR")) {
			int rnd = (int) (Math.random()*jg.getMazo().size());
			Carta c = jg.getMazo().get(rnd);
			jg.getMazo().remove(rnd);
			jg.getMano().add(c);
			repaint();
		}
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
