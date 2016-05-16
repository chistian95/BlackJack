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
	//ArrayList con los botones a pintar
	private List<Boton> botones;
	private BufferedImage bf;
	//Bandera para ver si el juego ha terminado
	private boolean terminado;
	//Botón de reset que al principio está en null
	private Boton botonReset;

	public Pantalla(Juego jg) {
		this.jg = jg;
		botones = new ArrayList<Boton>();
		terminado = false;
		botonReset = null;
		
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
		//Pintar el fondo
		bff.setColor(new Color(0, 126, 0));
		bff.fillRect(0, 0, getWidth(), getHeight());
		
		bff.setFont(fuente);
		
		//Pintar todas las cartas del mazo una encima de la otra
		for(int i=0; i<jg.getMazo().size(); i++) {
			int x1 = getWidth()/2+48;
			int y1 = getHeight()/2-(2*i);
			bff.setColor(new Color(126, 0, 0));			
			bff.fillRect(x1, y1, 32, 48);
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
		
		//Pintar las cartas que tiene el jugador principal
		for(int i=0; i<jg.getMano().getCartas().size(); i++) {
			//Coger la carta correspondiente y calcular su posición
			Carta c = jg.getMano().getCartas().get(i);
			int x1 = getWidth()/2-128 + 34*i;
			int y1 = getHeight()/2 + 64;
			
			//Pintar el color base de la carta
			bff.setColor(Color.WHITE);
			bff.fillRect(x1, y1, 32, 48);
			
			//Pintar el numero y el palo de su color correspondiente
			bff.setColor(Color.BLACK);
			bff.drawString(Numero.NUMEROS[c.getNumero()], x1+12, y1+24);
			if(c.getPalo() >= 2) {
				bff.setColor(Color.RED);
			}
			bff.drawString(Palo.SIMBOLOS[c.getPalo()], x1+12, y1+34);
			
			//Pintar el borde de la carta
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
		
		//Pitnar las cartas que tiene la casa 
		//(Aquí no hace falta recoger el objeto de la carta ya que no hace falta pintar el palo ni el numero)
		for(int i=0; i<jg.getCasa().getCartas().size(); i++) {
			//Calcular su posición
			int x1 = getWidth()/2-128 + 34*i;
			int y1 = getHeight()/2-200 + 64;
			
			//Pintar la base de la carta
			bff.setColor(new Color(126, 0, 0));
			bff.fillRect(x1, y1, 32, 48);
			
			//Pintar el borde de la carta
			bff.setColor(Color.BLACK);
			bff.drawRect(x1, y1, 32, 48);
		}
		
		//Pintar los puntos que lleva el jugador principal
		bff.setColor(Color.WHITE);
		bff.setFont(fuente);
		bff.drawString(jg.getMano().getPuntos()+"", getWidth()/2 - 150, getHeight()/2+64);
				
		//Pintar todos los botones
		for(Boton boton : botones) {
			//Cambiar el color de los botones dependiendo de si la partida ha terminado ya o no
			bff.setColor(new Color(0, 0, 126));
			if(terminado && !boton.getAccion().equals("REINICIAR")) {
				bff.setColor(new Color(64, 64, 64));
			}			
			//Pintar el color de fondo del botón
			bff.fillRect(boton.getX(), boton.getY(), boton.getDX(), boton.getDY());
			
			//Pintar el borde del botón
			bff.setColor(Color.BLACK);
			bff.drawRect(boton.getX(), boton.getY(), boton.getDX(), boton.getDY());
			
			//Pintar el texto del botón
			bff.setColor(Color.WHITE);
			bff.setFont(fuente);
			bff.drawString(boton.getAccion(), boton.getX()+16, boton.getY()+20);
		}
		
		//Comprobar si la partida ha terminado
		if(terminado) {	
			//Hacer que la casa coja una carta (Si es que puede)
			turnoCasa();
			
			//Mostrar todas las cartas de la casa
			for(int i=0; i<jg.getCasa().getCartas().size(); i++) {
				//Coger objeto carta y calcular su posición
				Carta c = jg.getCasa().getCartas().get(i);
				int x1 = getWidth()/2-128 + 34*i;
				int y1 = getHeight()/2-200 + 64;
				
				//Pintar la base de la carta
				bff.setColor(Color.WHITE);
				bff.fillRect(x1, y1, 32, 48);
				
				//Pintar el numero y el palo de su color correspondiente
				bff.setColor(Color.BLACK);
				bff.drawString(Numero.NUMEROS[c.getNumero()], x1+12, y1+24);
				if(c.getPalo() >= 2) {
					bff.setColor(Color.RED);
				}
				bff.drawString(Palo.SIMBOLOS[c.getPalo()], x1+12, y1+34);
				
				//Pintar el borde de la carta
				bff.setColor(Color.BLACK);
				bff.drawRect(x1, y1, 32, 48);
			}
			
			//Pintar los puntos que tiene la casa
			bff.setColor(Color.WHITE);
			bff.setFont(fuente);
			bff.drawString(jg.getCasa().getPuntos()+"", getWidth()/2 - 150, getHeight()/2-200+64);
			
			//Pintar el resultado de la partida
			bff.setColor(Color.WHITE);
			bff.setFont(fuente);
			if(jg.getMano().getPuntos() > 21) {
				bff.drawString("Has perdido!", getWidth()/2-50, getHeight()/2-150);
			} else if(jg.getMano().getPuntos() <= jg.getCasa().getPuntos() && jg.getCasa().getPuntos() <= 21) {
				bff.drawString("Has perdido!", getWidth()/2-50, getHeight()/2-150);
			} else {
				bff.drawString("Has ganado!", getWidth()/2-50, getHeight()/2-150);
			}
		}
			
		g.drawImage(bf, 0, 0, null);
	}
	
	//Hacer que la casa coja una carta (Si puede)
	private boolean turnoCasa() {
		boolean res = false;
		//Comprobar si ha pasado el limite de puntos establecido
		if(jg.getCasa().getPuntos() < 16) {
			int rnd = (int) (Math.random()*jg.getMazo().size());
			Carta c = jg.getMazo().get(rnd);
			//Quitar la carta del mazo y ponerla en la mano de la casa
			jg.getMazo().remove(rnd);
			jg.getCasa().getCartas().add(c);
			res = true;
		}		
		repaint();
		return res;
	}
	
	//Método a ejecutar cuando se pulse un botón
	private void pulsarBoton(Boton b) {
		//Botón de repartir carta al jugador principal
		if(!terminado && b.getAccion().equals("REPARTIR")) {
			int rnd = (int) (Math.random()*jg.getMazo().size());
			Carta c = jg.getMazo().get(rnd);
			//Quitar la carta del mazo y ponerla en la mano del jugador
			jg.getMazo().remove(rnd);
			jg.getMano().getCartas().add(c);
			//Comprobar si se ha pasado de puntos
			if(jg.getMano().getPuntos() >= 21) {
				terminado = true;
			}			
		//Botón para terminar la partida
		} else if(!terminado && b.getAccion().equals("PLANTARSE")) {
			terminado = true;
		//Botón para reiniciar la partida
		} else if(terminado && b.getAccion().endsWith("REINICIAR")) {
			//Reiniciar las variables necesarias
			botones = new ArrayList<Boton>();
			terminado = false;
			botonReset = null;
			//Llamar al método inicializar de la clase Juego
			jg.inicializar();
			//Meter los botones de repartir y plantarse
			Boton boton = new Boton(getWidth()/2+200, getHeight()/2, 110, 30, "REPARTIR");
			Boton boton2 = new Boton(getWidth()/2+200, getHeight()/2-60, 115, 30, "PLANTARSE");
			meterBoton(boton);
			meterBoton(boton2);
			//Salir para evitar que la casa haga su turno ahora
			return;
		}
		//Comprobar si el juego ha terminado en este turno y meter el botón de reiniciar
		if(terminado && botonReset == null) {
			botonReset = new Boton(getWidth()/2+200, getHeight()/2+60, 110, 30, "REINICIAR");
			botones.add(botonReset);
		}
		turnoCasa();		
	}
	
	public void meterBoton(Boton boton) {
		botones.add(boton);
	}

	//Método para comprobar si se ha pulsado algún botón
	@Override
	public void mouseClicked(MouseEvent e) {
		//Mirar si la posición coincide con alguno de los botones
		for(Boton boton : botones) {
			if(e.getX() >= boton.getX() && e.getX() <= boton.getX()+boton.getDX() && e.getY() >= boton.getY() && e.getY() <= boton.getY()+boton.getDY()) {
				pulsarBoton(boton);
				break;
			}
		}
	}
	
	//Método para comprobar si se ha pulsado alguna tecla
	@Override
	public void keyPressed(KeyEvent e) {
		//Si se ha pulsado el ESCAPE, salir del programa
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
