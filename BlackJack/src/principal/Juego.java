package principal;

import java.util.ArrayList;
import java.util.List;

import carta.Carta;
import carta.Numero;
import carta.Palo;
import pantalla.Boton;
import pantalla.Pantalla;

public class Juego {
	//ArraList con las cartas en el mazo
	private List<Carta> mazo;
	//Jugadores mano(Jugador principal) y casa
	private Jugador mano;
	private Jugador casa;
	private Pantalla pt;
	
	Juego() {
		inicializar();
		pt = new Pantalla(this);
		
		//Crear los botones de repartir y plantarse
		Boton boton = new Boton(pt.getWidth()/2+200, pt.getHeight()/2-60, 110, 30, "REPARTIR");
		Boton boton2 = new Boton(pt.getWidth()/2+200, pt.getHeight()/2, 115, 30, "PLANTARSE");
		pt.meterBoton(boton);
		pt.meterBoton(boton2);
	}
	
	public void inicializar() {
		//Inicializar las variables, crear el mazo y barajarlo;
		mazo = new ArrayList<Carta>();
		mano = new Jugador();
		casa = new Jugador();
		crearMazo();
		barajar();
	}
	
	private void crearMazo() {
		//Doble for para recorrer todos los numeros de cada palo
		for(int palo=0; palo<Palo.PALOS.length; palo++) {
			for(int numero=0; numero<Numero.NUMEROS.length; numero++) {
				//Crear objeto carta y añadirlo al mazo
				Carta c = new Carta(palo, numero);
				mazo.add(c);
			}
		}
	}
	
	private void barajar() {
		//Barajar 1000 veces el mazo cambiando de forma aleatoria la posición de dos cartas
		for(int i=0; i<1000; i++) {
			int rnd1 = (int) (Math.random()*mazo.size());
			int rnd2 = (int) (Math.random()*mazo.size());
			Carta cTemp = mazo.get(rnd1);
			mazo.set(rnd1, mazo.get(rnd2));
			mazo.set(rnd2, cTemp);
		}
	}
	
	public List<Carta> getMazo() {
		return mazo;
	}
	
	public Jugador getMano() {
		return mano;
	}
	
	public Jugador getCasa() {
		return casa;
	}
	
	public static void main(String[] args) {
		new Juego();
	}
}
