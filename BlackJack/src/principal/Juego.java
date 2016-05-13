package principal;

import java.util.ArrayList;
import java.util.List;

import carta.Carta;
import carta.Numero;
import carta.Palo;
import pantalla.Boton;
import pantalla.Pantalla;

public class Juego {
	private List<Carta> mazo;
	private Mano mano;
	private Pantalla pt;
	
	Juego() {
		mazo = new ArrayList<Carta>();
		mano = new Mano();
		crearMazo();
		barajar();
		pt = new Pantalla(this);
		
		Boton boton = new Boton(pt.getWidth()/2+200, pt.getHeight()/2, 110, 30, "REPARTIR");
		pt.meterBoton(boton);
	}
	
	private void crearMazo() {
		for(int palo=0; palo<Palo.PALOS.length; palo++) {
			for(int numero=0; numero<Numero.NUMEROS.length; numero++) {
				Carta c = new Carta(palo, numero);
				mazo.add(c);
			}
		}
	}
	
	private void barajar() {
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
	
	public Mano getMano() {
		return mano;
	}
	
	public static void main(String[] args) {
		new Juego();
	}
}
